package dam.adri.domain.apiModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dam.adri.domain.api.LeagueApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LeagueApiModule {

    @Singleton
    @Provides
    fun provideLeagueApiService(retrofit: Retrofit): LeagueApiService {
        return retrofit.create(LeagueApiService::class.java)
    }
}