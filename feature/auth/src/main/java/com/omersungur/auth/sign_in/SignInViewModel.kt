package com.omersungur.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.domain.use_cases.auth.login.LoginUseCase
import com.omersungur.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun loginWithGoogle() {
        //TODO: Implement login with Google
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(
                    isHaveError = true,
                    //errorMessage = context.getString(R.string.email_and_password_cannot_be_empty),
                )
            }
        } else if (password.length < 6) {
            _uiState.update {
                it.copy(
                    isHaveError = true,
                    //errorMessage = context.getString(R.string.password_must_be_at_least_6_characters_long),
                )
            }
        } else {
            loginUseCase(email, password).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                isSuccessEmailAndPasswordLogin = true,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                isHaveError = true,
                                errorMessage = it.errorMessage ?: "",
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateErrorStatesWithDefaultValues() {
        _uiState.update {
            it.copy(
                isHaveError = false,
                errorMessage = "",
            )
        }
    }

    data class LoginScreenUIState(
        var isLoading: Boolean = false,
        val isHaveError: Boolean = false,
        val errorMessage: String = "",
        val isSuccessGoogleLogin: Boolean = false,
        val isSuccessEmailAndPasswordLogin: Boolean = false,
        val isLoggedIn: Boolean = false,
    )
}