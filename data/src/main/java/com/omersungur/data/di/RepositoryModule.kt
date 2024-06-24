package com.omersungur.data.di

import com.google.firebase.auth.FirebaseAuth
import com.omersungur.data.local.favorite.FavoriteDao
import com.omersungur.data.remote.ProductApi
import com.omersungur.data.repository.FirebaseAuthenticationRepositoryImpl
import com.omersungur.data.repository.JWTAuhRepositoryImpl
import com.omersungur.data.repository.cart.CartRepositoryImpl
import com.omersungur.data.repository.favorite.FavoriteProductRepositoryImpl
import com.omersungur.data.repository.product.ProductRepositoryImpl
import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import com.omersungur.domain.repository.auth.JWTAuthRepository
import com.omersungur.domain.repository.cart.CartRepository
import com.omersungur.domain.repository.favorite.FavoriteProductRepository
import com.omersungur.domain.repository.product.ProductRepository
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

    @Provides
    @Singleton
    fun provideProductRepository(
        productApi: ProductApi
    ): ProductRepository = ProductRepositoryImpl(productApi)

    @Provides
    @Singleton
    fun provideFavoriteProductRepository(
        favoriteDao: FavoriteDao
    ): FavoriteProductRepository = FavoriteProductRepositoryImpl(favoriteDao)

    @Provides
    @Singleton
    fun provideCartRepository(
        productApi: ProductApi
    ): CartRepository = CartRepositoryImpl(productApi)
}