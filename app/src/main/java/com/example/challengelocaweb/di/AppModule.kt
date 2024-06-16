package com.example.challengelocaweb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.challengelocaweb.data.AppDatabase
import com.example.challengelocaweb.data.manager.LocalUserManagerImpl
import com.example.challengelocaweb.data.remote.EmailAPI
import com.example.challengelocaweb.data.repository.EmailRepositoryImpl
import com.example.challengelocaweb.data.repository.EventRepositoryImpl
import com.example.challengelocaweb.domain.dao.EventDao
import com.example.challengelocaweb.domain.manager.LocalUserManager
import com.example.challengelocaweb.domain.repository.EmailRepository
import com.example.challengelocaweb.domain.repository.EventRepository
import com.example.challengelocaweb.domain.useCases.appEntry.AppEntryUseCases
import com.example.challengelocaweb.domain.useCases.appEntry.ReadAppEntry
import com.example.challengelocaweb.domain.useCases.appEntry.SaveAppEntry
import com.example.challengelocaweb.domain.useCases.emails.EmailUseCases
import com.example.challengelocaweb.domain.useCases.emails.GetEmails
import com.example.challengelocaweb.util.Constansts.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideEventDao(database: AppDatabase): EventDao {
        return database.eventDao()
    }
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        LocalUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(LocalUserManager),
        saveAppEntry = SaveAppEntry(LocalUserManager)
    )

    @Provides
    @Singleton
    fun provideEmailAPI(): EmailAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmailAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideEmailRepository(
        emailAPI: EmailAPI
    ): EmailRepository = EmailRepositoryImpl(emailAPI)

    @Provides
    @Singleton
    fun provideEmailUseCases(
        emailRepository: EmailRepository
    ): EmailUseCases {
        return EmailUseCases(
            getEmails = GetEmails(emailRepository)
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository
}