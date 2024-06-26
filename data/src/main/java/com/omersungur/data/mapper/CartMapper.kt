package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.cart.AddCartRequestDto
import com.omersungur.data.remote.dto.cart.CartDto
import com.omersungur.data.remote.dto.cart.CartProductDto
import com.omersungur.data.remote.dto.cart.CartXDto
import com.omersungur.data.remote.dto.cart.ProductCartDto
import com.omersungur.domain.model.cart.AddCartRequest
import com.omersungur.domain.model.cart.Cart
import com.omersungur.domain.model.cart.CartProduct
import com.omersungur.domain.model.cart.CartX
import com.omersungur.domain.model.cart.ProductCart

fun CartDto.toCart(): Cart {
    return Cart(
        carts = carts?.map { it.toCartX() },
        limit = limit,
        skip = skip,
        total = total,
    )
}

fun CartXDto.toCartX(): CartX {
    return CartX(
        discountedTotal = discountedTotal,
        id = id,
        products = products?.map { it.toProduct() },
        total = total,
        totalProducts = totalProducts,
        totalQuantity = totalQuantity,
        userId = userId,
    )
}

fun ProductCartDto.toProduct(): ProductCart {
    return ProductCart(
        discountPercentage = discountPercentage,
        discountedTotal = discountedTotal,
        id = id,
        price = price,
        quantity = quantity,
        thumbnail = thumbnail,
        title = title,
        total = total,
    )
}

fun AddCartRequest.toAddCartRequestDto(): AddCartRequestDto {
    return AddCartRequestDto(
        userId = userId,
        products = products.map { it.toCartProductDto() }
    )
}

fun CartProduct.toCartProductDto(): CartProductDto {
    return CartProductDto(
        id = id,
        quantity = quantity
    )
}