package dam.adri.domain.initializer

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dam.adri.domain.repository.DriverRepository
import dam.adri.domain.repository.GpRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InitializerModule {

    @Singleton
    @Provides
    fun provideRepositoryInitializer(
        driverRepository: DriverRepository,
        gpRepository: GpRepository
    ): RepositoryInitializer {
        return RepositoryInitializer(
            driverRepository,
            gpRepository
        )
    }
}
