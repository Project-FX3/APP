package dam.adri.domain.repositoryImpl

import dam.adri.domain.api.LeagueApiService
import dam.adri.domain.modelo.dto.toLeagueDto
import dam.adri.domain.modelo.entities.League
import dam.adri.domain.modelo.entities.toLeague
import dam.adri.domain.repository.LeagueRepository
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(
    private val leagueApiService: LeagueApiService
) : LeagueRepository {

    private var cachedLeagues: List<League>? = null
    private var cachedLeagueByAccessCode: Map<String, League?> = emptyMap()
    private var cachedLeagueById: Map<Int, League?> = emptyMap()

    override suspend fun findAll(): List<League> {
        return cachedLeagues ?: run {
            val response = leagueApiService.list()
            if (response.isSuccessful) {
                val leagues = response.body()?.map { it.toLeague() } ?: emptyList()
                cachedLeagues = leagues
                leagues
            } else {
                throw Exception("Error fetching leagues: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun findByAccessCode(accesscode: String): League {
        return cachedLeagueByAccessCode[accesscode] ?: run {
            val response = leagueApiService.getByAccessCode(accesscode)
            if (response.isSuccessful) {
                val league = response.body()?.toLeague() ?: throw Exception("League not found")
                cachedLeagueByAccessCode = cachedLeagueByAccessCode + (accesscode to league)
                league
            } else {
                throw Exception("League not found")
            }
        }
    }

    override suspend fun findById(id: Int): League {
        return cachedLeagueById[id] ?: run {
            val response = leagueApiService.getById(id)
            if (response.isSuccessful) {
                val league = response.body()?.toLeague() ?: throw Exception("League not found")
                cachedLeagueById = cachedLeagueById + (id to league)
                league
            } else {
                throw Exception("Error fetching league: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun save(league: League): League {
        val response = leagueApiService.add(league.toLeagueDto())
        if (response.isSuccessful) {
            val savedLeague = response.body()?.toLeague() ?: throw Exception("Error saving league")
            cachedLeagues = null // Clear the cached list since a new league was added
            return savedLeague
        } else {
            throw Exception("Error saving league: ${response.errorBody()?.string()}")
        }
    }
}
