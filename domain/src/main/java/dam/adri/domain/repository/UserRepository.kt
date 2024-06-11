package dam.adri.domain.repository
import dam.adri.domain.modelo.entities.User


interface UserRepository {
   suspend fun findAll(): List<User>
   suspend fun findById(id: Int): User
   suspend fun save(user: User)
   suspend fun login(name: String, password: String): User
}


