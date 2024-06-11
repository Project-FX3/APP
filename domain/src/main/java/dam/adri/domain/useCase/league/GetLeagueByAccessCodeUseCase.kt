package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.repository.LeagueRepository
import javax.inject.Inject

class GetLeagueByAccessCodeUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    suspend fun getLeagueByAccessCode(accesscode: String): League {
        return try {
             leagueRepository.findByAccessCode(accesscode)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}