package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.repository.LeagueRepository
import dam.adri.domain.useCase.userLeague.GetUserLeagueByLeagueIdUseCase
import javax.inject.Inject

class GetLeagueByAccessCodeUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
    private val getUserLeagueByLeagueIdUseCase: GetUserLeagueByLeagueIdUseCase
) {
    suspend fun getLeagueByAccessCode(accesscode: String): League {
        return try {
            val league = leagueRepository.findByAccessCode(accesscode)
            league.size = getUserLeagueByLeagueIdUseCase.getNumberOfUsersByLeagueId(league.id!!)
            league
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}