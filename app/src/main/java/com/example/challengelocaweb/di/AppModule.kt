package com.example.challengelocaweb.di

import android.app.Application
import com.example.challengelocaweb.data.manager.LocalUserManagerImpl
import com.example.challengelocaweb.data.remote.NewsAPI
import com.example.challengelocaweb.data.repository.NewsRepositoryImpl
import com.example.challengelocaweb.domain.manager.LocalUserManager
import com.example.challengelocaweb.domain.repository.NewsRepository
import com.example.challengelocaweb.domain.useCases.appEntry.AppEntryUseCases
import com.example.challengelocaweb.domain.useCases.appEntry.ReadAppEntry
import com.example.challengelocaweb.domain.useCases.appEntry.SaveAppEntry
import com.example.challengelocaweb.domain.useCases.news.GetNews
import com.example.challengelocaweb.domain.useCases.news.NewsUseCases
import com.example.challengelocaweb.util.Constansts.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideNewsAPI(): NewsAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsAPI: NewsAPI
    ): NewsRepository = NewsRepositoryImpl(newsAPI)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository)
        )
    }
}