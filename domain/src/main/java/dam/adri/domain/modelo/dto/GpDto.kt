package dam.adri.domain.modelo.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam.adri.domain.modelo.entities.Gp
import java.math.BigDecimal
import java.util.Date

@JsonClass(generateAdapter = true)
data class GpDto(
    @Json(name = "id") val id: Int,
    @Json(name = "url") val url: String,
    @Json(name = "circuitname") val circuitname: String,
    @Json(name = "locality") val locality: String,
    @Json(name = "country") val country: String,
    @Json(name = "racename") val racename: String,
    @Json(name = "racedate") val racedate: String?,
    @Json(name = "racetime") val racetime: String?,
    @Json(name = "raceResults") val raceResults: Map<Int, Int>?,
    @Json(name = "firstpracticedate") val firstpracticedate: String?,
    @Json(name = "firstpracticetime") val firstpracticetime: String?,
    @Json(name = "firstPracticeResults") val firstPracticeResults: Map<Int, Int>?,
    @Json(name = "secondpracticedate") val secondpracticedate: String?,
    @Json(name = "secondpracticetime") val secondpracticetime: String?,
    @Json(name = "secondPracticeResults") val secondPracticeResults: Map<Int, Int>?,
    @Json(name = "thirdpracticedate") val thirdpracticedate: String?,
    @Json(name = "thirdpracticetime") val thirdpracticetime: String?,
    @Json(name = "thirdPracticeResults") val thirdPracticeResults: Map<Int, Int>?,
    @Json(name = "qualifyingdate") val qualifyingdate: String?,
    @Json(name = "qualifyingtime") val qualifyingtime: String?,
    @Json(name = "qualifyingResults") val qualifyingResults: Map<Int, Int>?,
    @Json(name = "qualifyingSprintdate") val qualifyingSprintdate: String?,
    @Json(name = "qualifyingSprinttime") val qualifyingSprinttime: String?,
    @Json(name = "qualifyingSprintResults") val qualifyingSprintResults: Map<Int, Int>?,
    @Json(name = "sprintdate") val sprintdate: String?,
    @Json(name = "sprinttime") val sprinttime: String?,
    @Json(name = "sprintResults") val sprintResults: Map<Int, Int>?
)

fun Gp.toGpDto(): GpDto {
    return GpDto(
        id = this.id,
        url = this.url,
        circuitname = this.circuitname,
        locality = this.locality,
        country = this.country,
        racename = this.racename,
        racedate = this.racedate,
        racetime = this.racetime,
        raceResults = this.raceResults,
        firstpracticedate = this.firstpracticedate,
        firstpracticetime = this.firstpracticetime,
        firstPracticeResults = this.firstPracticeResults,
        secondpracticedate = this.secondpracticedate,
        secondpracticetime = this.secondpracticetime,
        secondPracticeResults = this.secondPracticeResults,
        thirdpracticedate = this.thirdpracticedate,
        thirdpracticetime = this.thirdpracticetime,
        thirdPracticeResults = this.thirdPracticeResults,
        qualifyingdate = this.qualifyingdate,
        qualifyingtime = this.qualifyingtime,
        qualifyingResults = this.qualifyingResults,
        qualifyingSprintdate = this.qualifyingSprintdate,
        qualifyingSprinttime = this.qualifyingSprinttime,
        qualifyingSprintResults = this.qualifyingSprintResults,
        sprintdate = this.sprintdate,
        sprinttime = this.sprinttime,
        sprintResults = this.sprintResults
    )
}