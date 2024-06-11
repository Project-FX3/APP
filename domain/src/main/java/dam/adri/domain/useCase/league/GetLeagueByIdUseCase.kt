package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.repository.LeagueRepository
import dam.adri.domain.useCase.userLeague.GetUserLeagueByLeagueIdUseCase
import javax.inject.Inject

class GetLeagueByIdUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
    private val getUserLeagueByLeagueIdUseCase: GetUserLeagueByLeagueIdUseCase
) {
    suspend fun getLeagueById(id: Int): League? {
        return try {
          val league =  leagueRepository.findById(id)
            league.size = getUserLeagueByLeagueIdUseCase.getNumberOfUsersByLeagueId(id)
             league
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}
