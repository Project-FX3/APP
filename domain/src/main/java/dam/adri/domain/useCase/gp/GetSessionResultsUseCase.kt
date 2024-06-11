package dam.adri.domain.useCase.gp

import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.repository.DriverRepository
import dam.adri.domain.repository.GpRepository
import javax.inject.Inject

class GetSessionResultsUseCase @Inject constructor(
    private val gpRepository: GpRepository,
    private val driverRepository: DriverRepository
) {
    suspend fun getSessionResults(
        sessionType: String,
        idGp: Int
    ): List<Driver> {
        try {
            val sessionResults = gpRepository.getSessionResults(sessionType, idGp)
            sessionResults.let { results ->

                val sortedResults = results.toList().sortedBy { it.second }

                val drivers = sortedResults.map { (driverNumber, _) ->
                    try {
                        driverRepository.findByNumber(driverNumber)
                    } catch (e: Exception) {
                        createTemporaryDriver(driverNumber)
                    }
                }
                return drivers
            }
        } catch (throwable: Throwable) {

            println("Error in getSessionResults: ${throwable.message}")
            throw throwable
        }
    }

    private fun createTemporaryDriver(driverNumber: Int): Driver {
        return Driver(
            driverNumber = driverNumber,
            countryCode = null,
            image = null,
            lastName = null,
            teamColour = null,
            teamName = null,
            nameAcronym = null
        )
    }
}
