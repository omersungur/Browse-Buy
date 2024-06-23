package com.omersungur.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omersungur.data.local.favorite.FavoriteDao
import com.omersungur.data.local.favorite.entity.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class BrowseAndBuyDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao
}