package dam.adri.domain.useCase.league

import dam.adri.domain.modelo.entities.League
import dam.adri.domain.repository.LeagueRepository
import javax.inject.Inject

class GetLeagueByIdUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    suspend fun getLeagueById(id: Int): League? {
        return try {
            leagueRepository.findById(id)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}
