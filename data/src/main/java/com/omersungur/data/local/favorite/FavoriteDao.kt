package com.omersungur.data.local.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omersungur.data.local.favorite.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProductEntity)

    @Delete
    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProductEntity)

    @Query("SELECT * FROM favoriteproductentity")
    fun getFavoriteProduct(): Flow<List<FavoriteProductEntity>>
}