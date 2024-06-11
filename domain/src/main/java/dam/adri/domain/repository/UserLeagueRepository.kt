package dam.adri.domain.repository

import dam.adri.domain.modelo.entities.UserLeague

interface UserLeagueRepository {

    suspend fun getUserLeaguesByUserId(userId: Int): List<UserLeague>

    suspend fun getUserLeaguesByLeagueId(leagueId: Int): List<UserLeague>

    suspend fun getUserLeagueByUserIdAndLeagueId(userId: Int, leagueId: Int): UserLeague?

    suspend fun addUserLeague(userLeague: UserLeague)

    suspend fun deleteUserLeague(userId: Int, leagueId: Int)
}
