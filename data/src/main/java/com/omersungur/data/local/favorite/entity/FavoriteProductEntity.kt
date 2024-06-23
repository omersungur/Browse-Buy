package com.omersungur.data.local.favorite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String?,
    val brand: String?,
    val imageUrl: String?,
    val rating: Double?,
)