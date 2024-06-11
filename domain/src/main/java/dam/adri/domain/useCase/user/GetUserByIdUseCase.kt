package dam.adri.domain.useCase.user

import dam.adri.domain.modelo.entities.User
import javax.inject.Inject
import dam.adri.domain.repository.UserRepository


class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserById(id: Int): User {
        return userRepository.findById(id)
    }
}


