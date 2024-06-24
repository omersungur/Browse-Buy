package com.omersungur.data.repository.product

import com.omersungur.data.mapper.toCategoryItem
import com.omersungur.data.mapper.toProduct
import com.omersungur.data.remote.ProductApi
import com.omersungur.domain.model.category.Category
import com.omersungur.domain.model.product.Product
import com.omersungur.domain.repository.product.ProductRepository
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : ProductRepository {

    override fun getProducts(): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())
            val product = productApi.getProducts()
            emit(Resource.Success(product.toProduct()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = productApi.getCategories()
            emit(Resource.Success(categories.map { it.toCategoryItem() }))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override fun getProductsByCategory(categoryName: String): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())
            val categories = productApi.getProductsByCategory(categoryName)
            emit(Resource.Success(categories.toProduct()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }
}