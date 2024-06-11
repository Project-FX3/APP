package dam.adri.domain.modelo.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam.adri.domain.modelo.entities.League

@JsonClass(generateAdapter = true)
data class LeagueDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "accesscode") val accesscode: String?
)

fun League.toLeagueDto(): LeagueDto {
    return LeagueDto(
        id = this.id,
        name = this.name,
        accesscode = this.accesscode
    )
}