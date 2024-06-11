package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.repository.LeagueRepository
import javax.inject.Inject

class CreateLeagueUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {

    suspend fun createLeague(leagueName: String): League {
        val league = League(
            id = null,
            name = leagueName,
            accesscode = "",
            size = 0
        )
        return leagueRepository.save(league)
    }
}