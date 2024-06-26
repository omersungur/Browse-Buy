package com.omersungur.home.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.model.cart.AddCartRequest
import com.omersungur.domain.model.cart.CartProduct
import com.omersungur.domain.model.cart.CartX
import com.omersungur.domain.model.category.Category
import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.model.product.ProductX
import com.omersungur.domain.repository.favorite.FavoriteProductRepository
import com.omersungur.domain.repository.product.ProductRepository
import com.omersungur.domain.use_cases.auth.logout.LogoutUseCase
import com.omersungur.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val logOutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        getProducts()
        getCategories()
        getFavoriteProduct()
    }

    fun logOutFromFirebase() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

    fun getProducts(query: String = "") = productRepository.getProducts().onEach { result ->
        when (result) {
            is Resource.Loading -> {
                _uiState.update { state ->
                    state.copy(loadingState = true)
                }
            }

            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        loadingState = false,
                        isHaveError = false,
                        isSuccessForDetail = true,
                        products = result.data?.products?.filter {
                            it.title?.contains(query, ignoreCase = true) ?: false
                        } ?: emptyList()
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state ->
                    state.copy(
                        loadingState = false,
                        isHaveError = true,
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    private fun getCategories() = productRepository.getCategories().onEach { result ->
        when (result) {
            is Resource.Loading -> {
                _uiState.update { state ->
                    state.copy(loadingState = true)
                }
            }

            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        loadingState = false,
                        isHaveError = false,
                        isSuccessForDetail = true,
                        categories = result.data ?: emptyList()
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state ->
                    state.copy(
                        loadingState = false,
                        isHaveError = true,
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    fun addProductsToCart(userId: Int, products: List<CartProduct>) = productRepository.addCart(
        AddCartRequest(userId, products)
    ).onEach { result ->
        when (result) {
            is Resource.Loading -> {
                _uiState.update { state ->
                    state.copy(isRequestSuccess = true)
                }
            }

            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        isHaveError = false,
                        isRequestSuccess = true,
                        cartResult = result.data
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state ->
                    state.copy(
                        isRequestSuccess = false,
                        isHaveError = true,
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)
    
    private fun getFavoriteProduct() = favoriteProductRepository.getFavoriteProduct().onEach { result ->
        _uiState.update { state ->
            state.copy(
                isSuccessForDetail = true,
                favoriteProducts = result
            )
        }
    }.launchIn(viewModelScope)

    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        favoriteProductRepository.insertFavoriteProduct(favoriteProduct)
    }

    fun deleteFavoriteProduct(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        favoriteProductRepository.deleteFavoriteProduct(favoriteProduct)
    }

    fun isProductFavorite(productId: Int): Boolean {
        return _uiState.value.favoriteProducts.any { it.id == productId }
    }

    fun updateRequestSuccessStatesWithDefaultValues() {
        _uiState.update {
            it.copy(
                isRequestSuccess = false
            )
        }
    }


    data class HomeUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccessForDetail: Boolean = false,
        val errorMessage: String = "",
        val products: List<ProductX> = emptyList(),
        val categories : List<Category> = emptyList(),
        val favoriteProducts : List<FavoriteProduct> = emptyList(),
        val isRequestSuccess : Boolean = false,
        val cartResult : CartX? = null
    )
}