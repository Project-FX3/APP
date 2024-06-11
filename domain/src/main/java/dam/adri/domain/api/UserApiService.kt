package dam.adri.domain.api
import dam.adri.domain.modelo.dto.UserDto
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @GET("/user")
    suspend fun list(): Response<List<UserDto>>

    @GET("/user/login/{name}/{password}")
    suspend fun login(@Path("name") name: String, @Path("password") password: String):Response<UserDto>

    @GET("/user/{id}")
    suspend fun getById(@Path("id") id: Int):Response<UserDto>

    @POST("/user")
    suspend fun add(@Body userDTO: UserDto):Response<Unit>
}