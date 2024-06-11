package dam.adri.domain.repositoryImpl

import dam.adri.domain.api.UserLeagueApiService
import dam.adri.domain.modelo.dto.toUserLeagueDto
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.domain.modelo.entities.toUserLeague
import dam.adri.domain.repository.UserLeagueRepository
import javax.inject.Inject

class UserLeagueRepositoryImpl @Inject constructor(
    private val userLeagueApiService: UserLeagueApiService
) : UserLeagueRepository {

    private var cachedUserLeaguesByUserId: Map<Int, List<UserLeague>> = emptyMap()
    private var cachedUserLeaguesByLeagueId: Map<Int, List<UserLeague>> = emptyMap()
    private var cachedUserLeagueByUserIdAndLeagueId: Map<Pair<Int, Int>, UserLeague?> = emptyMap()

    override suspend fun getUserLeaguesByUserId(userId: Int): List<UserLeague> {
        return cachedUserLeaguesByUserId[userId] ?: run {
            val response = userLeagueApiService.getUserLeaguesByUserId(userId)
            if (response.isSuccessful) {
                val userLeagues = response.body()?.map { it.toUserLeague() } ?: emptyList()
                cachedUserLeaguesByUserId = cachedUserLeaguesByUserId + (userId to userLeagues)
                userLeagues
            } else {
                throw Exception("Error fetching user leagues: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun getUserLeaguesByLeagueId(leagueId: Int): List<UserLeague> {
        return cachedUserLeaguesByLeagueId[leagueId] ?: run {
            val response = userLeagueApiService.getUserLeaguesByLeagueId(leagueId)
            if (response.isSuccessful) {
                val userLeagues = response.body()?.map { it.toUserLeague() } ?: emptyList()
                cachedUserLeaguesByLeagueId = cachedUserLeaguesByLeagueId + (leagueId to userLeagues)
                userLeagues
            } else {
                throw Exception("Error fetching user leagues: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun getUserLeagueByUserIdAndLeagueId(userId: Int, leagueId: Int): UserLeague? {
        return cachedUserLeagueByUserIdAndLeagueId[userId to leagueId] ?: run {
            val response = userLeagueApiService.getUserLeagueByUserIdAndLeagueId(userId, leagueId)
            if (response.isSuccessful) {
                val userLeague = response.body()?.toUserLeague()
                cachedUserLeagueByUserIdAndLeagueId = cachedUserLeagueByUserIdAndLeagueId + ((userId to leagueId) to userLeague)
                userLeague
            } else {
                throw Exception("Error fetching user league: ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun addUserLeague(userLeague: UserLeague) {
        val userDto = userLeague.toUserLeagueDto()
        val response = userLeagueApiService.addUserLeague(userDto)
        if (response.isSuccessful) {

            cachedUserLeaguesByUserId = cachedUserLeaguesByUserId.filterNot { it.key == userLeague.user }
            cachedUserLeaguesByLeagueId = cachedUserLeaguesByLeagueId.filterNot { it.key == userLeague.league }
            cachedUserLeagueByUserIdAndLeagueId = cachedUserLeagueByUserIdAndLeagueId.filterNot { it.key == Pair(userLeague.user, userLeague.league) }
        } else {
            throw Exception("Error adding user league:\n ${response.errorBody()?.string()}")
        }
    }

    override suspend fun deleteUserLeague(userId: Int, leagueId: Int) {
        val response = userLeagueApiService.deleteUserLeague(userId, leagueId)
        if (response.isSuccessful) {

            cachedUserLeaguesByUserId = cachedUserLeaguesByUserId.filterNot { it.key == userId }
            cachedUserLeaguesByLeagueId = cachedUserLeaguesByLeagueId.filterNot { it.key == leagueId }
            cachedUserLeagueByUserIdAndLeagueId = cachedUserLeagueByUserIdAndLeagueId.filterNot { it.key == Pair(userId, leagueId) }
        } else {
            throw Exception("Error deleting user league: ${response.errorBody()?.string()}")
        }
    }
}
