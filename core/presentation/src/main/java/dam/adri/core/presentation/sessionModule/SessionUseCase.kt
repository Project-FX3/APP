package dam.adri.core.presentation.sessionModule

import dam.adri.core.presentation.userManager.UserManager
import dam.adri.domain.modelo.entities.User
import dam.adri.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton


class SessionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager
) {
    fun getUserId(): Int? = userManager.getUserId()

    fun isUserLoggedIn(): Boolean = userManager.isUserLoggedIn()

    fun logout() {
        userManager.logout()
    }

    fun logIn(id: Int) {
        userManager.logIn(id)
    }

}


