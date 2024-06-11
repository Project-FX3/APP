package dam.adri.domain.api

import dam.adri.domain.modelo.dto.DriverDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DriverApiService {

    @GET("/driver")
    suspend fun list(): Response<List<DriverDto>>

    @GET("/driver/{number}")
    suspend fun get(@Path("number") number: Int): Response<DriverDto>

    @GET("/driver/user/{idUser}/league/{idLeague}")
    suspend fun getByUserIdLeagueId(@Path("idUser") idUser: Int, @Path("idLeague") idLeague: Int): Response<List<DriverDto>>
}