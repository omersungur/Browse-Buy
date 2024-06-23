package com.omersungur.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.repository.favorite.FavoriteProductRepository
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
class FavoriteViewModel @Inject constructor(
    private val favoriteProductRepository: FavoriteProductRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUIState())
    val uiState: StateFlow<FavoriteUIState> = _uiState.asStateFlow()

    init {
        getFavoriteProduct()
    }

    private fun getFavoriteProduct() = favoriteProductRepository.getFavoriteProduct().onEach { result ->
        _uiState.update { state ->
            state.copy(
                isSuccess = true,
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

    data class FavoriteUIState(
        val isSuccess: Boolean = false,
        val favoriteProducts : List<FavoriteProduct> = emptyList()
    )
}