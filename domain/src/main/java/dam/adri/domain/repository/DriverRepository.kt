package dam.adri.domain.repository

import dam.adri.domain.modelo.entities.Driver

interface DriverRepository {
    suspend fun findAll(): List<Driver>
    suspend fun findByNumber(number: Int): Driver
    suspend fun findByUserIdAndLeagueId(idUser: Int, idLeague: Int): List<Driver>
}


