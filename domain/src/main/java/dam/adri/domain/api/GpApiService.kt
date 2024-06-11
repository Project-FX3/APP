package dam.adri.domain.api
import dam.adri.domain.modelo.dto.GpDto
import retrofit2.Response
import retrofit2.http.*

interface GpApiService {

    @GET("/gp")
    suspend fun list(): Response<List<GpDto>>

    @GET("/gp/{id}")
    suspend fun get(@Path("id") id: Int): Response<GpDto>

    @GET("/gp/next")
    suspend fun getNextGp(): Response<GpDto>

    @GET("/gp/session/{sessionType}/{idGp}")
    suspend fun getSessionResults(@Path("sessionType") sessionType: String, @Path("idGp") idGp: Int): Response<Map<Int, Int>>

    @GET("/gp/withoutResults")
    suspend fun listWithoutResults(): Response<List<GpDto>>
}