package dam.adri.domain.useCase.user

import dam.adri.domain.modelo.entities.User
import dam.adri.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun login(name: String, password: String): User {
        return userRepository.login(name, password)
    }
}

