package com.omersungur.data.remote

import com.omersungur.data.remote.dto.cart.CartDto
import com.omersungur.data.remote.dto.category.CategoryDto
import com.omersungur.data.remote.dto.jwt_user.JWTDataDto
import com.omersungur.data.remote.dto.jwt_user.JWTUserDto
import com.omersungur.data.remote.dto.product.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

    @POST("auth/login")
    suspend fun login(@Body jwtUser: JWTUserDto): JWTDataDto

    @GET("products")
    suspend fun getProducts(): ProductDto

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("carts/user/{userId}")
    suspend fun getUserCart(@Path("userId") userId: Int): CartDto

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): ProductDto
}