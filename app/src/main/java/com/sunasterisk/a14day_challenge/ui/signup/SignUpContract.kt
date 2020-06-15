package com.sunasterisk.a14day_challenge.ui.signup

interface SignUpContract {
    interface View {
        fun changeToLoginScreen()

        fun showErrorSignUp(error: String)
    }

    interface Presenter {
        fun handleSignUp(
            confirmPassword: String,
            username: String,
            name: String,
            password: String,
            birthday: String,
            height: String,
            weight: String
        )
    }
}
