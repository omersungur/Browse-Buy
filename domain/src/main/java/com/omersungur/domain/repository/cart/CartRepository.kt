package com.omersungur.domain.repository.cart

import com.omersungur.domain.model.cart.Cart
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow


interface CartRepository {

    fun getUserCart(userId: Int): Flow<Resource<Cart>>
}