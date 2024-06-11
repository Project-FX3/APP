package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.useCase.user.CheckUserLeagueAssociationUseCase
import javax.inject.Inject

class GetLeaguesByUserUseCase @Inject constructor(
    private val checkUserLeagueAssociationUseCase: CheckUserLeagueAssociationUseCase,
    private val getLeagueByIdUseCase: GetLeagueByIdUseCase
) {
    suspend fun getLeaguesByUser(userId: Int): List<League> {
        return try {
            val userLeagues = checkUserLeagueAssociationUseCase.checkUserLeagues(userId)
            val leagueIds = userLeagues.mapNotNull { it.league }
            val leagues = leagueIds.mapNotNull { leagueId ->
                getLeagueByIdUseCase.getLeagueById(leagueId)
            }
            leagues
        } catch (e: Throwable) {
            throw e
        }
    }
}
