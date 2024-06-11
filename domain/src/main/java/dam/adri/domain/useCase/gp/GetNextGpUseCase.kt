package dam.adri.domain.useCase.gp

import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.repository.GpRepository
import dam.adri.domain.repositoryImpl.GpRepositoryImpl
import javax.inject.Inject

class GetNextGpUseCase @Inject constructor(
    private val gpRepository: GpRepository
) {
    suspend fun getNextGp(): Gp {
        try {
            return gpRepository.findNextGp()
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

}