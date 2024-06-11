package dam.adri.domain.useCase.gp

import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.repository.GpRepository
import javax.inject.Inject

class GetGpByIdUseCase @Inject constructor(
    private val gpRepository: GpRepository
) {
    suspend fun getGpById(id: Int): Gp {
        return try {
            gpRepository.findById(id)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}

