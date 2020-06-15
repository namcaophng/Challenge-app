package com.sunasterisk.a14day_challenge.ui.login

import com.sunasterisk.a14day_challenge.data.model.User

interface LoginContract {
    interface View {
        fun changeToHomeScreen(user: User)

        fun showErrorLogin(error: String)
    }

    interface Presenter {
        fun handleLogin(account: String, password: String)
    }
}
