package dam.adri.domain.modelo.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam.adri.domain.modelo.entities.Driver


@JsonClass(generateAdapter = true)
data class DriverDto(
    @Json(name = "driverNumber") val driverNumber: Int,
    @Json(name = "countryCode") val countryCode: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "lastName") val lastName: String?,
    @Json(name = "teamColour") val teamColour: String?,
    @Json(name = "teamName") val teamName: String?,
    @Json(name = "nameAcronym") val nameAcronym: String?
)

fun Driver.toDriverDto(): DriverDto {
    return DriverDto(
        driverNumber = this.driverNumber,
        countryCode = this.countryCode,
        image = this.image,
        lastName = this.lastName,
        teamColour = this.teamColour,
        teamName = this.teamName,
        nameAcronym = this.nameAcronym
    )
}
