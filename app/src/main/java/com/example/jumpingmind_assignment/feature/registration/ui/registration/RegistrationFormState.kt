package com.example.jumpingmind_assignment.feature.registration.ui.registration

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.jumpingmind_assignment.R

/**
 * Data validation state of the login form.
 */

class RegistrationFormState() : BaseObservable() {
    @get:Bindable
    var isDataValid: Boolean = false
        set(value) {
            field = value
            notifyChange()

        }

    @get:Bindable
    var emailError: Int= R.string.empty
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    var passwordError: Int=R.string.empty
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    var userNameError: Int=R.string.empty
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var bioError: Int=R.string.empty
        set(value) {
            field = value
            notifyChange()
        }


}