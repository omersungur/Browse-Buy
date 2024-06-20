package com.omersungur.data.di

import com.google.firebase.auth.FirebaseAuth
import com.omersungur.data.remote.ProductApi
import com.omersungur.data.repository.FirebaseAuthenticationRepositoryImpl
import com.omersungur.data.repository.JWTAuhRepositoryImpl
import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import com.omersungur.domain.repository.auth.JWTAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJWTAuthRepository(
        productApi: ProductApi
    ): JWTAuthRepository = JWTAuhRepositoryImpl(productApi)

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        auth: FirebaseAuth
    ): FirebaseAuthenticationRepository = FirebaseAuthenticationRepositoryImpl(auth)
}