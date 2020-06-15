package com.sunasterisk.a14day_challenge.ui.signup

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback
import com.sunasterisk.a14day_challenge.data.model.User

class SignUpPresenter(
    var view: SignUpContract.View,
    private val userRepository: UserRepository
) : SignUpContract.Presenter {

    override fun handleSignUp(
        confirmPassword: String,
        username: String,
        name: String,
        password: String,
        birthday: String,
        height: String,
        weight: String
    ) {
        val user = User(
            username,
            name,
            password,
            birthday,
            height,
            weight,
            process = 1
        )
        if (user.account.isEmpty() || user.password.isEmpty() || user.name.isEmpty()) {
            view.showErrorSignUp(Resources.getSystem().getString(R.string.error_signup_empty))
            return
        }
        if (!user.password.contentEquals(confirmPassword)) {
            view.showErrorSignUp(Resources.getSystem().getString(R.string.error_confirm_password))
            return
        }

        return userRepository.addUser(
            user,
            object : OnLoadedDataCallback<Boolean?> {
                override fun onSuccessful(data: Boolean?) {
                    view.changeToLoginScreen()
                }

                override fun onFailed(error: String) {
                    view.showErrorSignUp(error)
                }
            })
    }
}
