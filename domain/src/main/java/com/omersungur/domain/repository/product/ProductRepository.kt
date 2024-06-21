package com.omersungur.domain.repository.product

import com.omersungur.domain.model.product.Product
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<Resource<Product>>
}