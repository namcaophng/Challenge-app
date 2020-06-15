package com.sunasterisk.a14day_challenge.ui.login

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.model.User

class LoginPresenter(
    var view: LoginContract.View,
    private val userRepository: UserRepository
) :
    LoginContract.Presenter {

    override fun handleLogin(account: String, password: String) {
        val userList = userRepository.getAllUsers()
        lateinit var user: User
        var userAccount: String
        var userPassword: String
        var isValidUser = false

        userList?.forEach {
            userAccount = it.account
            userPassword = it.password

            if (userAccount.contentEquals(account) && userPassword.contentEquals(password)) {
                isValidUser = true
                user = it
            }
        }

        if (isValidUser) {
            view.changeToHomeScreen(user)
        } else if (account.isEmpty() || password.isEmpty()) {
            view.showErrorLogin(Resources.getSystem().getString(R.string.error_login_empty))
        } else {
            view.showErrorLogin(Resources.getSystem().getString(R.string.error_login_account))
        }
    }
}
