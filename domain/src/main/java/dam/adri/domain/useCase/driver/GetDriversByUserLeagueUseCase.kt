package dam.adri.domain.useCase.driver

import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.repository.DriverRepository
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class GetDriversByUserLeagueUseCase @Inject constructor(
    private val userLeagueRepository: UserLeagueRepository,
    private val driverRepository: DriverRepository
) {
    suspend fun getDriversByUserLeague(
        userId: Int,
        leagueId: Int
    ): List<Driver> {
        return try {
            val userLeague = userLeagueRepository.getUserLeagueByUserIdAndLeagueId(userId, leagueId)
            val driversNumbers = userLeague?.let {
                listOfNotNull(
                    it.driver1Number,
                    it.driver2Number,
                    it.driver3Number,
                    it.driver4Number
                )
            } ?: emptyList()

            driversNumbers.map { driverNumber ->
                driverRepository.findByNumber(driverNumber)
            }
        } catch (e: Throwable) {
            emptyList()
        }
    }
}
