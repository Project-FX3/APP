package dam.adri.domain.modelo.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam.adri.domain.modelo.entities.UserLeague

@JsonClass(generateAdapter = true)
data class UserLeagueDto(
    @Json(name = "league") val league: Int?,
    @Json(name = "user") val user: Int?,
    @Json(name="userName") val userName: String?,
    @Json(name = "driver4Number") val driver4Number: Int?,
    @Json(name = "driver2Number") val driver2Number: Int?,
    @Json(name = "driver3Number") val driver3Number: Int?,
    @Json(name = "driver1Number") val driver1Number: Int?,
    @Json(name = "puntuation") val puntuation: Int?
)


fun UserLeague.toUserLeagueDto(): UserLeagueDto {
    return UserLeagueDto(
        league = this.league,
        user = this.user,
        userName = this.userName,
        driver4Number = this.driver4Number,
        driver2Number = this.driver2Number,
        driver3Number = this.driver3Number,
        driver1Number = this.driver1Number,
        puntuation = this.puntuation
    )
}