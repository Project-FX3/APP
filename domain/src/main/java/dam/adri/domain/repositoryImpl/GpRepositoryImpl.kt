package dam.adri.domain.repositoryImpl

import dam.adri.domain.api.GpApiService
import dam.adri.domain.modelo.entities.Gp
import dam.adri.domain.modelo.entities.toGp
import dam.adri.domain.repository.GpRepository
import javax.inject.Inject

class GpRepositoryImpl @Inject constructor(
    private val gpApiService: GpApiService
) : GpRepository {

    private var cachedGps: List<Gp>? = null
    private var cachedGpById: Map<Int, Gp?> = emptyMap()
    private var cachedNextGp: Gp? = null
    private var cachedSessionResults: Map<Pair<String, Int>, Map<Int, Int>> = emptyMap()
    private var cachedGpsWithoutResults: List<Gp>? = null

    override suspend fun findAll(): List<Gp> {
        return cachedGps ?: run {
            val response = gpApiService.list()
            if (response.isSuccessful) {
                val gps = response.body()?.map { it.toGp() } ?: emptyList()
                cachedGps = gps
                cachedGpsWithoutResults = gps
                cachedGpById = gps.associateBy { it.id }
                gps
            } else {
                throw Throwable("Error occurred while fetching GPs")
            }
        }
    }

    override suspend fun findById(id: Int): Gp {
        return cachedGpById[id] ?: run {
            val response = gpApiService.get(id)
            if (response.isSuccessful) {
                val gp = response.body()?.toGp() ?: throw Throwable("GP not found")
                cachedGpById = cachedGpById + (id to gp)
                gp
            } else {
                throw Throwable("Error occurred while fetching GP")
            }
        }
    }

    override suspend fun findNextGp(): Gp {
        return cachedNextGp ?: run {
            val response = gpApiService.getNextGp()
            if (response.isSuccessful) {
                val nextGp = response.body()?.toGp() ?: throw Throwable("Next GP not found")
                cachedNextGp = nextGp
                nextGp
            } else {
                throw Throwable("Error occurred while fetching next GP")
            }
        }
    }

    override suspend fun getSessionResults(sessionType: String, idGp: Int): Map<Int, Int> {
        return cachedSessionResults[sessionType to idGp] ?: run {
            val response = gpApiService.getSessionResults(sessionType, idGp)
            if (response.isSuccessful) {
                val results = response.body() ?: throw Throwable("Session results not found")
                cachedSessionResults = cachedSessionResults + ((sessionType to idGp) to results)
                results
            } else {
                throw Throwable("Error occurred while fetching session results")
            }
        }
    }

    override suspend fun findAllWithoutResults(): List<Gp> {
        return cachedGpsWithoutResults ?: run {
            val response = gpApiService.listWithoutResults()
            if (response.isSuccessful) {
                val gps = response.body()?.map { it.toGp() } ?: emptyList()
                cachedGpsWithoutResults = gps
                gps
            } else {
                throw Throwable("Error occurred while fetching GPs without results")
            }
        }
    }
}
