package dam.adri.domain.repository

import dam.adri.domain.modelo.entities.League

interface LeagueRepository {
    suspend fun findAll(): List<League>
    suspend fun findByAccessCode(accesscode: String): League
    suspend fun findById(id: Int): League
    suspend fun save(league: League): League
}

