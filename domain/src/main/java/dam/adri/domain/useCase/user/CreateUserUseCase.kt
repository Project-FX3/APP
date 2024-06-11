package dam.adri.domain.useCase.user
import dam.adri.core.data.utils.PasswordValidator
import dam.adri.domain.modelo.entities.User
import dam.adri.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun createUser(username: String, password: String){

        val validationErrors = PasswordValidator.validate(
            password,
            minLength = 8,
            requireUppercase = true,
            requireLowercase = true,
            requireDigits = true,
            requireSpecialChar = true
        )

        if (validationErrors.isNotEmpty()) {
            throw Throwable(validationErrors.joinToString(separator = "\n"))
        }


        val newUser = User(
            id = null,
            name = username,
            password = password
        )
       userRepository.save(newUser)
    }
}
