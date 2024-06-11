package dam.adri.domain.api
import dam.adri.domain.modelo.dto.UserLeagueDto
import retrofit2.http.*
import retrofit2.Response


interface UserLeagueApiService {

    @GET("/userleague/user/{userId}")
    suspend fun getUserLeaguesByUserId(@Path("userId") userId: Int): Response<List<UserLeagueDto>>

    @GET("/userleague/league/{leagueId}")
    suspend fun getUserLeaguesByLeagueId(@Path("leagueId") leagueId: Int): Response<List<UserLeagueDto>>

    @GET("/userleague/{userId}/{leagueId}")
    suspend fun getUserLeagueByUserIdAndLeagueId(
        @Path("userId") userId: Int,
        @Path("leagueId") leagueId: Int
    ): Response<UserLeagueDto>

    @POST("/userleague")
    suspend fun addUserLeague(@Body userLeagueDto: UserLeagueDto): Response<Unit>

    @DELETE("/userleague/{userId}/{leagueId}")
    suspend fun deleteUserLeague(
        @Path("userId") userId: Int,
        @Path("leagueId") leagueId: Int
    ): Response<Unit>
}
