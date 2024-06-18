package com.omersungur.data.di

import com.google.firebase.auth.FirebaseAuth
import com.omersungur.data.repository.FirebaseAuthenticationRepositoryImpl
import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAuthenticationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        auth: FirebaseAuth
    ): FirebaseAuthenticationRepository = FirebaseAuthenticationRepositoryImpl(auth)
}
