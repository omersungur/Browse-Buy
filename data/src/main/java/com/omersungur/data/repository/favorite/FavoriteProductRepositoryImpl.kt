package com.omersungur.data.repository.favorite

import com.omersungur.data.local.favorite.FavoriteDao
import com.omersungur.data.mapper.toFavoriteProduct
import com.omersungur.data.mapper.toFavoriteProductEntity
import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.repository.favorite.FavoriteProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteProductRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteProductRepository {

    override suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        favoriteDao.insertFavoriteProduct(favoriteProduct = favoriteProduct.toFavoriteProductEntity())
    }

    override suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct) {
        favoriteDao.deleteFavoriteProduct(favoriteProduct = favoriteProduct.toFavoriteProductEntity())
    }

    override fun getFavoriteProduct(): Flow<List<FavoriteProduct>> {
        return favoriteDao.getFavoriteProduct().map { favoriteProductEntityList ->
            favoriteProductEntityList.map {
                it.toFavoriteProduct()
            }
        }
    }
}