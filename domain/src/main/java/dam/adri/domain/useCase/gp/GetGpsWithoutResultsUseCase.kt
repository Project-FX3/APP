package dam.adri.domain.useCase.gp

import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.repository.GpRepository
import javax.inject.Inject

class GetGpsWithoutResultsUseCase @Inject constructor(
    private val gpRepository: GpRepository
) {
    suspend fun getGpsWithoutResults(): List<Gp> {
        return try {
            gpRepository.findAllWithoutResults().sortedBy { it.id }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}
