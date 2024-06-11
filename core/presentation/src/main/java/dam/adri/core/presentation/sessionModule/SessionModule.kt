package dam.adri.core.presentation.sessionModule

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dam.adri.core.presentation.userManager.UserManager
import javax.inject.Singleton
import dam.adri.core.data.ConstantInfo.SHARED_PREFERENCES
import dam.adri.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Singleton
    @Provides
    fun provideUserManager(sharedPreferences: SharedPreferences): UserManager {
        return UserManager(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSessionUseCase(
        userRepository: UserRepository,
        userManager: UserManager
    ): SessionUseCase {
        return SessionUseCase(userRepository, userManager)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}
