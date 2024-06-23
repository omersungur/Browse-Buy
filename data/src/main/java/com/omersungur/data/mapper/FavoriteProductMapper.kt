package com.omersungur.data.mapper

import com.omersungur.data.local.favorite.entity.FavoriteProductEntity
import com.omersungur.domain.model.favorite.FavoriteProduct

fun FavoriteProduct.toFavoriteProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = id,
        name = name,
        brand = brand,
        imageUrl = imageUrl,
        rating = rating,
    )
}

fun FavoriteProductEntity.toFavoriteProduct(): FavoriteProduct {
    return FavoriteProduct(
        id = id,
        name = name,
        brand = brand,
        imageUrl = imageUrl,
        rating = rating,
    )
}