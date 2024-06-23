package com.omersungur.domain.repository.favorite

import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {

    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)

    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct)

    fun getFavoriteProduct(): Flow<List<FavoriteProduct>>
}