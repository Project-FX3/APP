package dam.adri.domain.useCase.driver

import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.repository.DriverRepository
import javax.inject.Inject

class ListAllDriversUseCase @Inject constructor(
    private val driverRepository: DriverRepository
) {
    suspend fun listAllDrivers(): List<Driver> {
        return try {
            driverRepository.findAll()
        } catch (e: Throwable) {
            throw e
        }
    }
}
