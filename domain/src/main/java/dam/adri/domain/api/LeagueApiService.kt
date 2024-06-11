package dam.adri.domain.api

import dam.adri.domain.modelo.dto.LeagueDto
import retrofit2.Response
import retrofit2.http.*

interface LeagueApiService {

    @GET("/league")
   suspend fun list(): Response<List<LeagueDto>>

    @GET("/league/accesscode/{accesscode}")
    suspend fun getByAccessCode(@Path("accesscode") accesscode: String): Response<LeagueDto>

    @GET("/league/{id}")
    suspend fun getById(@Path("id") id: Int): Response<LeagueDto>

    @POST("/league")
    suspend fun add(@Body leagueDTO: LeagueDto): Response<LeagueDto>
}