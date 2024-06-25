package com.omersungur.data.remote

import com.omersungur.data.remote.dto.cart.AddCartRequestDto
import com.omersungur.data.remote.dto.cart.CartDto
import com.omersungur.data.remote.dto.cart.CartXDto
import com.omersungur.data.remote.dto.category.CategoryDto
import com.omersungur.data.remote.dto.jwt_user.JWTDataDto
import com.omersungur.data.remote.dto.jwt_user.JWTUserDto
import com.omersungur.data.remote.dto.product.ProductDto
import com.omersungur.data.remote.dto.product.ProductXDto
import com.omersungur.data.remote.dto.user.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

    @POST("auth/login")
    suspend fun login(@Body jwtUser: JWTUserDto): JWTDataDto

    @GET("products")
    suspend fun getProducts(): ProductDto

    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId: Int): ProductXDto

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("carts/user/{userId}")
    suspend fun getUserCart(@Path("userId") userId: Int): CartDto

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): ProductDto

    @GET("users/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: Int): UserDto

    @POST("carts/add")
    suspend fun addCart(@Body addCartRequest: AddCartRequestDto): CartXDto
}