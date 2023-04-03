package com.example.jumpingmind_assignment.feature.registration.ui.registration

import android.net.Uri
import android.util.Patterns
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jumpingmind_assignment.R
import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.registration.data.LoginSignUpResponse
import com.example.jumpingmind_assignment.feature.registration.data.SignUpRequest
import com.example.jumpingmind_assignment.feature.registration.repo.RepoRegistrationInterface
import com.example.jumpingmind_assignment.utils.singleEvent.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repoRegistrationInterface: RepoRegistrationInterface) :
    ViewModel() {
    /**
     * Observable field
     */
    val email = ObservableField("")
    val password = ObservableField("")
    val username = ObservableField("")
    val bio = ObservableField("")
    private val profile = ObservableField("")
    var camUri  =MutableLiveData<Uri>()
    val registrationFormState = RegistrationFormState()

    val action = SingleLiveEvent<Action>()

    fun updateImage(uri: Uri) {
        profile.set(uri.toString())
        camUri.postValue(uri)
    }

    private val _result = MutableLiveData<LoginSignUpResponse>()
    val result: LiveData<LoginSignUpResponse> = _result

    /**
     * error
     */

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * loading handling
     */
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        setObservableCallbacks()
    }

    fun register() {
        val request = SignUpRequest(
            email.get() ?: "",
            password.get() ?: "",
            username.get() ?: "",
            profile.get() ?: "",
            bio.get() ?: ""
        )
        viewModelScope.launch {
            repoRegistrationInterface.signUp(request).collect { rslt ->
                when (rslt) {
                    is RepoResult.Success -> {
                        rslt.data?.let {
                            if (it.status) {
                                insertUserInDB(request)
                            } else {
                                _result.postValue(it)
                            }
                        }
                    }
                    is RepoResult.Error -> {
                        _error.postValue(rslt.data)
                    }
                    is RepoResult.Loading -> {
                        _loading.postValue(rslt.loadingStatus)
                    }
                    is RepoResult.Exception -> {

                    }

                }
            }
        }
    }

    private fun insertUserInDB(request: SignUpRequest) {
        viewModelScope.launch {
            repoRegistrationInterface.insertUser(request).collect { rslt ->
                when (rslt) {
                    is RepoResult.Success -> {

                        rslt.data?.let {
                            _result.postValue(it)
                        }
                    }
                    is RepoResult.Error -> {
                        _error.postValue(rslt.data)
                    }
                    is RepoResult.Loading -> {
                        _loading.postValue(rslt.loadingStatus)
                    }
                    is RepoResult.Exception -> {

                    }

                }
            }
        }
    }

    fun gotoLogin() {
        action.postValue(Action.gotoLogin)
    }

    fun getImage() {
        action.postValue(Action.getImage)
    }


    private fun dataChanged(email: String, username: String, password: String, bio: String) {
        registrationFormState.isDataValid = true
        if (!isUserNameValid(username) || !isEmailValid(email) || !isPasswordValid(password) || !isBioValid(
                bio
            )
        ) {
            registrationFormState.isDataValid = false
        }


    }

    // A placeholder username validation check
    private fun isEmailValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length > 2 && username.isNotEmpty()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5 && password.isNotEmpty()
    }

    private fun isBioValid(bio: String): Boolean {
        return bio.length > 5 && bio.isNotEmpty()
    }


    private fun setObservableCallbacks() {

        email.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isEmailValid(email.get() ?: "")) {
                    registrationFormState.emailError = R.string.invalid_email
                } else {
                    registrationFormState.emailError = R.string.empty
                }
                dataChanged(
                    email.get() ?: "",
                    username.get() ?: "",
                    password.get() ?: "",
                    bio.get() ?: ""
                )
            }

        })
        password.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isPasswordValid(password.get() ?: "")) {
                    registrationFormState.passwordError = R.string.invalid_password
                } else {
                    registrationFormState.passwordError = R.string.empty
                }
                dataChanged(
                    email.get() ?: "",
                    username.get() ?: "",
                    password.get() ?: "",
                    bio.get() ?: ""
                )
            }

        })
        username.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isUserNameValid(username.get() ?: "")) {
                    registrationFormState.userNameError = R.string.invalid_username
                } else {
                    registrationFormState.userNameError = R.string.empty
                }
                dataChanged(
                    email.get() ?: "",
                    username.get() ?: "",
                    password.get() ?: "",
                    bio.get() ?: ""
                )
            }

        })
        bio.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!isBioValid(bio.get() ?: "")) {
                    registrationFormState.bioError = R.string.invalid_bio
                } else {
                    registrationFormState.bioError = R.string.empty
                }
                dataChanged(
                    email.get() ?: "",
                    username.get() ?: "",
                    password.get() ?: "",
                    bio.get() ?: ""
                )
            }

        })
    }

    enum class Action {
        gotoLogin, getImage
    }
}