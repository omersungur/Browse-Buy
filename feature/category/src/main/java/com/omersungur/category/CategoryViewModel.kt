package com.omersungur.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.model.category.Category
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
class CategoryViewModel @Inject constructor(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUIState())
    val uiState: StateFlow<CategoryUIState> = _uiState.asStateFlow()

    init {
        getCategories()
    }

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
                        isSuccess = true,
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

    fun getProductsByCategory(categoryName: String) = productRepository.getProductsByCategory(categoryName).onEach { result ->
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
                        categorySuccess = true,
                        categoryProducts = result.data?.products ?: emptyList()
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

    fun updateSuccessState() {
        _uiState.update { state ->
            state.copy(isSuccess = false)
        }
    }

    data class CategoryUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val categories : List<Category> = emptyList(),
        val categoryProducts : List<ProductX> = emptyList(),
        val categorySuccess : Boolean = false
    )
}