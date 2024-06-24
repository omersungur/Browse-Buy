package com.omersungur.data.repository.cart

import com.omersungur.data.mapper.toCart
import com.omersungur.data.remote.ProductApi
import com.omersungur.domain.model.cart.Cart
import com.omersungur.domain.repository.cart.CartRepository
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : CartRepository {

    override fun getUserCart(userId: Int): Flow<Resource<Cart>> = flow {
        try {
            emit(Resource.Loading())
            val response = productApi.getUserCart(userId)
            emit(Resource.Success(response.toCart()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}