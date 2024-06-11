package dam.adri.domain.modelo.entities

import dam.adri.domain.modelo.dto.DriverDto

data class Driver(
    val driverNumber: Int,
    val countryCode: String?,
    val image: String?,
    val lastName: String?,
    val teamColour: String?,
    val teamName: String?,
    val nameAcronym: String?
)

fun DriverDto.toDriver(): Driver {
    return Driver(
        driverNumber = this.driverNumber,
        countryCode = this.countryCode,
        image = this.image,
        lastName = this.lastName,
        teamColour = this.teamColour,
        teamName = this.teamName,
        nameAcronym = this.nameAcronym
    )
}