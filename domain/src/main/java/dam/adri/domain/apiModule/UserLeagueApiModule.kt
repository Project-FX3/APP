package dam.adri.domain.apiModule

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dam.adri.core.data.ConstantInfo.BASE_URL
import dam.adri.domain.api.UserLeagueApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLeagueApiModule {

    @Singleton
    @Provides
    fun provideUserLeagueApiService(retrofit: Retrofit): UserLeagueApiService {
        return retrofit.create(UserLeagueApiService::class.java)
    }
}