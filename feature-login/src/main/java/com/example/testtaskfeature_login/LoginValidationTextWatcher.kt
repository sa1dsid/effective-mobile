package com.example.task3

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class LoginValidationTextWatcher(
    private val buttonLogin: Button,
    private val inputEmail: TextInputEditText,
    private val inputPassword: TextInputEditText
) : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable?) {
        buttonLogin.isEnabled =
            emailValidation(inputEmail.text.toString()) && inputPassword.text.toString().isNotBlank()
    }

    fun emailValidation(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(email.trim())
            .matches()
    }
}