package dam.adri.domain.useCase.user

import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class CheckUserLeagueAssociationUseCase @Inject constructor(
    private val userLeagueRepository: UserLeagueRepository
) {
    suspend fun checkUserLeagues(userId: Int): List<UserLeague> {
        return try {
            userLeagueRepository.getUserLeaguesByUserId(userId)
        } catch (e: Throwable) {
            throw e
        }
    }
}
