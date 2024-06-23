package com.omersungur.data.di

import android.content.Context
import androidx.room.Room
import com.omersungur.data.local.BrowseAndBuyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BrowseAndBuyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = BrowseAndBuyDatabase::class.java,
            name = "BROWSE_AND_BUY_DB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: BrowseAndBuyDatabase) = database.favoriteDao()
}