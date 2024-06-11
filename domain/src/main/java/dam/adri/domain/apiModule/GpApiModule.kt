package dam.adri.domain.apiModule


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dam.adri.domain.api.GpApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GpApiModule {

    @Singleton
    @Provides
    fun provideGpApiService(retrofit: Retrofit): GpApiService {
        return retrofit.create(GpApiService::class.java)
    }
}
