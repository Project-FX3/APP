package dam.adri.domain.repositoryImpl

import dam.adri.domain.api.UserApiService
import dam.adri.domain.modelo.dto.toUserDto
import dam.adri.domain.modelo.entities.User
import dam.adri.domain.modelo.entities.toUser
import dam.adri.domain.repository.UserRepository
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRepository {

    private var cachedUsers: List<User>? = null
    private var cachedUserById: MutableMap<Int, User?> = mutableMapOf()
    private var cachedLoggedInUser: User? = null

    override suspend fun findAll(): List<User> {
        return cachedUsers ?: run {
            val response = userApiService.list()
            if (response.isSuccessful) {
                val users = response.body()?.map { it.toUser() } ?: emptyList()
                cachedUsers = users
                users
            } else {
                throw Exception("Error fetching users: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun findById(id: Int): User {
        return cachedUserById[id] ?: run {
            val response = userApiService.getById(id)
            if (response.isSuccessful) {
                val user = response.body()?.toUser()
                cachedUserById[id] = user
                user ?: throw Exception("User not found")
            } else {
                throw Exception("Error fetching user: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun save(user: User) {
        val userDto = user.toUserDto()
        val response = userApiService.add(userDto)
        if (response.isSuccessful) {
            if (response.code() == 200) {
                invalidateCache()
                return
            } else {
                throw HttpException(response)
            }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun login(name: String, password: String): User {
        return cachedLoggedInUser ?: run {
            val response = userApiService.login(name, password)
            if (response.isSuccessful) {
                val user = response.body()?.toUser() ?: throw Exception("Invalid login credentials")
                cachedLoggedInUser = user
                user
            } else {
                throw Exception("Error logging in: ${response.errorBody()?.string()}")
            }
        }
    }

    private fun invalidateCache() {
        cachedUsers = null
        cachedUserById.clear()
        cachedLoggedInUser = null
    }
}
