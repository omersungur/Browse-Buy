package com.omersungur.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.use_cases.auth.reset_password.ResetPasswordUseCase
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
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUIState())
    val uiState: StateFlow<ForgotPasswordUIState> = _uiState.asStateFlow()

    fun resetPassword(email: String) = resetPasswordUseCase(email).onEach { result ->
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

    fun updateSuccessStateWithDefaultValue() {
        _uiState.update {
            it.copy(isSuccess = false)
        }
    }

    fun updateErrorStateWithDefaultValue() {
        _uiState.update {
            it.copy(isHaveError = false)
        }
    }

    data class ForgotPasswordUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
    )
}