package dam.adri.domain.repository
import dam.adri.domain.modelo.entities.Gp


interface GpRepository {
    suspend fun findAll(): List<Gp>
    suspend fun findById(id: Int): Gp
    suspend fun findNextGp(): Gp
    suspend fun getSessionResults(sessionType: String, idGp: Int): Map<Int, Int>
    suspend fun findAllWithoutResults(): List<Gp>
}
