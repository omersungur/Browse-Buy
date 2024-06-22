package com.omersungur.search

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
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    init {
        filterProduct()
    }

    fun filterProduct(query: String = "") = productRepository.getProducts().onEach { result ->
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
                        isSuccess = true,
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

    data class SearchUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val products: List<ProductX> = emptyList()
    )
}