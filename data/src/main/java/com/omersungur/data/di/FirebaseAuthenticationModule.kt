package com.omersungur.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.omersungur.data.remote.ProductApi
import com.omersungur.data.repository.FirebaseAuthenticationRepositoryImpl
import com.omersungur.data.repository.JWTAuhRepositoryImpl
import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import com.omersungur.domain.repository.auth.JWTAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAuthenticationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}
