package dam.adri.domain.useCase.userLeague

import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class GetUserLeagueByLeagueIdUseCase @Inject constructor(
    private val userLeagueRepository: UserLeagueRepository
) {

    suspend fun getUserLeagueByLeagueId(leagueId: Int): List<UserLeague> {
        return try {
            userLeagueRepository.getUserLeaguesByLeagueId(leagueId)
        } catch (e: Throwable) {
            throw e
        }
    }

}