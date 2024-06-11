package dam.adri.domain.repositoryImpl

import dam.adri.domain.api.DriverApiService
import dam.adri.domain.modelo.entities.Driver
import dam.adri.domain.modelo.entities.toDriver
import dam.adri.domain.repository.DriverRepository
import javax.inject.Inject

class DriverRepositoryImpl @Inject constructor(
    private val driverApiService: DriverApiService
) : DriverRepository {

    private var cachedDrivers: List<Driver>? = null
    private var cachedDriverByNumber: Map<Int, Driver?> = emptyMap()
    private var cachedDriversByUserAndLeague: Map<Pair<Int, Int>, List<Driver>> = emptyMap()

    override suspend fun findAll(): List<Driver> {
        return cachedDrivers ?: run {
            val response = driverApiService.list()
            if (response.isSuccessful) {
                val drivers = response.body()?.map { it.toDriver() } ?: emptyList()
                cachedDrivers = drivers
                drivers
            } else {
                throw Exception("Error fetching drivers: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun findByNumber(number: Int): Driver {
        return cachedDriverByNumber[number] ?: run {
            val response = driverApiService.get(number)
            if (response.isSuccessful) {
                val driver = response.body()?.toDriver() ?: throw Exception("Driver not found")
                cachedDriverByNumber = cachedDriverByNumber + (number to driver)
                driver
            } else {
                throw Exception("Error fetching driver: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun findByUserIdAndLeagueId(idUser: Int, idLeague: Int): List<Driver> {
        return cachedDriversByUserAndLeague[idUser to idLeague] ?: run {
            val response = driverApiService.getByUserIdLeagueId(idUser, idLeague)
            if (response.isSuccessful) {
                val drivers = response.body()?.map { it.toDriver() } ?: emptyList()
                cachedDriversByUserAndLeague = cachedDriversByUserAndLeague + ((idUser to idLeague) to drivers)
                drivers
            } else {
                throw Exception("Error fetching drivers by user and league: ${response.errorBody()?.string()}")
            }
        }
    }
}
