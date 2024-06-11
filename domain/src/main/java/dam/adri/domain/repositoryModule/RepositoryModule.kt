package dam.adri.domain.repositoryModule


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dam.adri.domain.repository.DriverRepository
import dam.adri.domain.repository.GpRepository
import dam.adri.domain.repository.LeagueRepository
import dam.adri.domain.repository.UserLeagueRepository
import dam.adri.domain.repository.UserRepository
import dam.adri.domain.repositoryImpl.DriverRepositoryImpl
import dam.adri.domain.repositoryImpl.GpRepositoryImpl
import dam.adri.domain.repositoryImpl.LeagueRepositoryImpl
import dam.adri.domain.repositoryImpl.UserLeagueRepositoryImpl
import dam.adri.domain.repositoryImpl.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Singleton
    @Binds
    fun bindDriverRepository(driverRepository: DriverRepositoryImpl): DriverRepository
    @Singleton
    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindLeagueRepository(leagueRepository: LeagueRepositoryImpl): LeagueRepository

    @Singleton
    @Binds
    fun bindUserLeagueRepository(userleagueRepository: UserLeagueRepositoryImpl): UserLeagueRepository

    @Singleton
    @Binds
    fun bindGpsRepository(gpRepository: GpRepositoryImpl): GpRepository

}