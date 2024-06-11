package dam.adri.domain.useCase.userLeague

import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class GetUserLeagueByUserIdAndLeagueIdUseCase @Inject constructor(
    private val userLeagueRepository: UserLeagueRepository
) {
    suspend fun getUserLeagueByUserIdAndLeagueId(userId: Int, leagueId: Int): UserLeague? {
        return try {
            userLeagueRepository.getUserLeagueByUserIdAndLeagueId(userId, leagueId)
        } catch (e: Throwable) {
            throw e
        }
    }

    suspend fun deleteUserLeague(userId: Int, leagueId: Int): Unit {
     return try {
         userLeagueRepository.deleteUserLeague(userId, leagueId)
     } catch (e: Throwable) {
         throw e
     }
    }
}
