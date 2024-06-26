package com.omersungur.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.model.product.ProductX
import com.omersungur.domain.repository.product.ProductRepository
import com.omersungur.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() = productRepository.getProducts().onEach { result ->
        when (result) {
            is Resource.Loading -> {
                _uiState.update { state ->
                    state.copy(loadingStateForProducts = true)
                }
            }

            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        loadingStateForProducts = false,
                        isHaveError = false,
                        isSuccessForProducts = true,
                        products = result.data?.products?: emptyList()
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state ->
                    state.copy(
                        loadingStateForProducts = false,
                        isHaveError = true,
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    fun getProductById(productId: Int) = productRepository.getProductById(productId).onEach { result ->
        when (result) {
            is Resource.Loading -> {
                _uiState.update { state ->
                    state.copy(loadingStateForDetail = true)
                }
            }

            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        loadingStateForDetail = false,
                        isHaveError = false,
                        isSuccess = true,
                        product = result.data
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state ->
                    state.copy(
                        loadingStateForDetail = false,
                        isHaveError = true,
                        errorMessage = result.errorMessage.orEmpty()
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    data class DetailUIState(
        val loadingStateForDetail: Boolean = false,
        val loadingStateForProducts: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val isSuccessForProducts: Boolean = false,
        val errorMessage: String = "",
        val product: ProductX? = null,
        val products: List<ProductX> = emptyList(),
    )
}