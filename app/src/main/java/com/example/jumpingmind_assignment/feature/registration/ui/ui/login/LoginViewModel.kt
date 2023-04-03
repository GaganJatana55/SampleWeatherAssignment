package com.example.jumpingmind_assignment.feature.registration.ui.ui.login

import android.util.Patterns
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jumpingmind_assignment.R
import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreI
import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.registration.data.LoginRequest
import com.example.jumpingmind_assignment.feature.registration.data.LoginSignUpResponse
import com.example.jumpingmind_assignment.feature.registration.repo.RepositoryRegistration
import com.example.jumpingmind_assignment.utils.singleEvent.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: RepositoryRegistration,
    private val dataStoreI: DataStoreI
) :
    ViewModel() {

    val loginFormState = LoginFormState()

    private val _loginResult = MutableLiveData<LoginSignUpResponse>()
    val loginResult: LiveData<LoginSignUpResponse> = _loginResult

    /**
     * loginError
     */

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    /**
     * loading handling
     */
    private val _loginLoading = MutableLiveData<Boolean>()
    val loginLoading: LiveData<Boolean> = _loginLoading

    /**
     *
     */

    val action = SingleLiveEvent<Action>()

    /**
     * Observable field
     */
    val email = ObservableField("")
    val password = ObservableField("")

    init {
        setObservableCallbacks()
    }

    fun login() {
        login(email.get() ?: "", password.get() ?: "")
    }

    fun signUp() {
        action.postValue(Action.goSignUp)

    }


    private fun login(username: String, password: String) {
        // launched in a separate asynchronous job
        viewModelScope.launch {

            loginRepository.login(LoginRequest(username, password)).collect { result ->

                when (result) {
                    is RepoResult.Success -> {
                        result.data?.let {
                            if (it.status) {
                                dataStoreI.setUserLoggedIn(username)
                                action.postValue(Action.goDashBoard)
                            }
                            _loginResult.postValue(it)
                        }
                    }
                    is RepoResult.Error -> {
                        _loginError.postValue(result.data)
                    }
                    is RepoResult.Loading -> {
                        _loginLoading.postValue(result.loadingStatus)
                    }
                    is RepoResult.Exception -> {
                        _loginError.postValue(result.throwable.message)
                    }

                }
            }
        }

    }

    private fun loginDataChanged(username: String, password: String) {
        loginFormState.isDataValid = true
        if (!isUserNameValid(username)) {
            loginFormState.isDataValid = false
        } else if (!isPasswordValid(password)) {
            loginFormState.isDataValid = false
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


    private fun setObservableCallbacks() {
        email.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isUserNameValid(email.get() ?: "")) {
                    loginFormState.emailError = R.string.invalid_username
                } else {
                    loginFormState.emailError = R.string.empty
                }
                loginDataChanged(email.get() ?: "", password.get() ?: "")
            }

        })
        password.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isPasswordValid(password.get() ?: "")) {
                    loginFormState.passwordError = R.string.invalid_password

                } else {
                    loginFormState.passwordError = R.string.empty
                }
                loginDataChanged(email.get() ?: "", password.get() ?: "")

            }

        })
    }

    enum class Action {
        goSignUp, goDashBoard
    }
}