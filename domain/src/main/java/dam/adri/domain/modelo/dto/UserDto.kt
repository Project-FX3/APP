package dam.adri.domain.modelo.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam.adri.domain.modelo.entities.User


@JsonClass(generateAdapter = true)
data class UserDto (
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String,
    @Json(name = "password") val password: String
)

fun User.toUserDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name,
        password = this.password
    )
}