package com.example.challengelocaweb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.challengelocaweb.data.AppDatabase
import com.example.challengelocaweb.data.manager.LocalUserManagerImpl
import com.example.challengelocaweb.data.remote.EmailAPI
import com.example.challengelocaweb.data.repository.AttachmentRepositoryImpl
import com.example.challengelocaweb.data.repository.EmailRepositoryImpl
import com.example.challengelocaweb.data.repository.EventRepositoryImpl
import com.example.challengelocaweb.domain.dao.AttachmentDao
import com.example.challengelocaweb.domain.dao.EmailDao
import com.example.challengelocaweb.domain.dao.EventDao
import com.example.challengelocaweb.domain.manager.LocalUserManager
import com.example.challengelocaweb.domain.repository.AttachmentRepository
import com.example.challengelocaweb.domain.repository.EmailRepository
import com.example.challengelocaweb.domain.repository.EventRepository
import com.example.challengelocaweb.domain.useCases.appEntry.AppEntryUseCases
import com.example.challengelocaweb.domain.useCases.appEntry.ReadAppEntry
import com.example.challengelocaweb.domain.useCases.appEntry.SaveAppEntry
import com.example.challengelocaweb.domain.useCases.emails.DeleteEmail
import com.example.challengelocaweb.domain.useCases.emails.EmailUseCases
import com.example.challengelocaweb.domain.useCases.emails.GetDraftEmails
import com.example.challengelocaweb.domain.useCases.emails.GetEmails
import com.example.challengelocaweb.domain.useCases.emails.GetEmailsWithAttachments
import com.example.challengelocaweb.domain.useCases.emails.GetFavoritesEmails
import com.example.challengelocaweb.domain.useCases.emails.GetSendEmails
import com.example.challengelocaweb.domain.useCases.emails.GetSpamEmails
import com.example.challengelocaweb.domain.useCases.emails.GetUnreadEmailCount
import com.example.challengelocaweb.domain.useCases.emails.MarkAsNotSpam
import com.example.challengelocaweb.domain.useCases.emails.MarkAsRead
import com.example.challengelocaweb.domain.useCases.emails.MarkAsSecure
import com.example.challengelocaweb.domain.useCases.emails.MarkAsSpam
import com.example.challengelocaweb.domain.useCases.emails.MarkAsUnread
import com.example.challengelocaweb.domain.useCases.emails.SendEmail
import com.example.challengelocaweb.domain.useCases.emails.UpdateEmail
import com.example.challengelocaweb.domain.useCases.emails.UploadFile
import com.example.challengelocaweb.util.Constansts.BASE_URL
import com.example.challengelocaweb.util.ThemeSetting
import com.example.challengelocaweb.util.ThemeSettingPreference
import com.example.challengelocaweb.util.TokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
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
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
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
    fun provideAttachmentDao(database: AppDatabase): AttachmentDao {
        return database.attachmentDao()
    }

    @Provides
    fun provideEmailDao(database: AppDatabase): EmailDao {
        return database.emailDao()
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
    fun provideEmailUseCases(
        emailRepository: EmailRepository
    ): EmailUseCases {
        return EmailUseCases(
            getEmails = GetEmails(emailRepository),
            deleteEmail = DeleteEmail(emailRepository),
            updateEmail = UpdateEmail(emailRepository),
            favoritesEmails = GetFavoritesEmails(emailRepository),
            spamEmails = GetSpamEmails(emailRepository),
            markAsRead = MarkAsRead(emailRepository),
            markAsUnread = MarkAsUnread(emailRepository),
            markAsSpam = MarkAsSpam(emailRepository),
            markAsNotSpam = MarkAsNotSpam(emailRepository),
            markAsSecure = MarkAsSecure(emailRepository),
            getUnreadEmailCount = GetUnreadEmailCount(emailRepository),
            getEmailsWithAttachments = GetEmailsWithAttachments(emailRepository),
            sendEmail = SendEmail(emailRepository),
            draftEmails = GetDraftEmails(emailRepository),
            uploadFile = UploadFile(emailRepository),
            getSendEmails = GetSendEmails(emailRepository)
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAttachmentRepository(
        attachmentRepositoryImpl: AttachmentRepositoryImpl
    ): AttachmentRepository

    @Binds
    @Singleton
    abstract fun bindEmailRepository(
        emailRepositoryImpl: EmailRepositoryImpl
    ): EmailRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {

    @Binds
    @Singleton
    abstract fun bindThemeSetting(
        themeSettingPreference: ThemeSettingPreference
    ): ThemeSetting
}