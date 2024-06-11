package dam.adri.domain.useCase.userLeague

import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class CreateUserLeagueUseCase @Inject constructor(
    private val userLeagueRepository: UserLeagueRepository
) {
    suspend fun createUserLeague(userLeague: UserLeague) {
        return try {
            userLeagueRepository.addUserLeague(userLeague)
        } catch (e: Throwable) {
            throw e
        }
    }
}
