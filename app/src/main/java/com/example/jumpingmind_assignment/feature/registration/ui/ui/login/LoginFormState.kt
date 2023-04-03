package com.example.jumpingmind_assignment.feature.registration.ui.ui.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.jumpingmind_assignment.R

/**
 * Data validation state of the login form.
 */
//data class LoginFormState(
//    val emailError: Int? = null,
//    val passwordError: Int? = null,
//    val isDataValid: Boolean = false
//)

class LoginFormState () : BaseObservable(){
    @get:Bindable
    var isDataValid : Boolean = false
        set(value) {
            field = value
            notifyChange()

        }

    @get:Bindable
    var emailError : Int= R.string.empty
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    var passwordError : Int=R.string.empty
        set(value) {
            field = value
            notifyChange()
        }

}