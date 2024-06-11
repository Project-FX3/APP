package dam.adri.domain.modelo.entities

import dam.adri.domain.modelo.dto.GpDto
import java.math.BigDecimal
import java.util.Date

data class Gp(
    val id: Int,
    val url: String,
    val circuitname: String,
    val locality: String,
    val country: String,
    val racename: String,
    val racedate: String?,
    val racetime: String?,
    val raceResults: Map<Int, Int>?,
    val firstpracticedate: String?,
    val firstpracticetime: String?,
    val firstPracticeResults: Map<Int, Int>?,
    val secondpracticedate: String?,
    val secondpracticetime: String?,
    val secondPracticeResults: Map<Int, Int>?,
    val thirdpracticedate: String?,
    val thirdpracticetime: String?,
    val thirdPracticeResults: Map<Int, Int>?,
    val qualifyingdate: String?,
    val qualifyingtime: String?,
    val qualifyingResults: Map<Int, Int>?,
    val qualifyingSprintdate: String?,
    val qualifyingSprinttime: String?,
    val qualifyingSprintResults: Map<Int, Int>?,
    val sprintdate: String?,
    val sprinttime: String?,
    val sprintResults: Map<Int, Int>?
)

fun GpDto.toGp(): Gp {
    return Gp(
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