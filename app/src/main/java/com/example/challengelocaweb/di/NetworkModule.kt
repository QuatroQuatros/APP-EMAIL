package com.example.challengelocaweb.di

import android.content.Context
import com.example.challengelocaweb.BuildConfig
import com.example.challengelocaweb.api.Endpoint
import com.example.challengelocaweb.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideEndpoint(retrofit: Retrofit): Endpoint {
        return retrofit.create(Endpoint::class.java)
    }

    @Provides
    fun provideNetworkUtils(@ApplicationContext appContext: Context): NetworkUtils {
        return NetworkUtils(appContext)
    }
}