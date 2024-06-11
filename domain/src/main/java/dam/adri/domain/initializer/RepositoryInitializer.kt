package dam.adri.domain.initializer

import dam.adri.domain.repository.DriverRepository
import dam.adri.domain.repository.GpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryInitializer @Inject constructor(
    private val driverRepository: DriverRepository,
    private val gpRepository: GpRepository
) {

    suspend fun initializeRepositories() {
        withContext(Dispatchers.IO) {

            driverRepository.findAll()
            gpRepository.findAllWithoutResults()
        }
    }
}
