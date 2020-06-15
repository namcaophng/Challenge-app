package com.sunasterisk.a14day_challenge.ui.edit

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback

class EditUserPresenter(
    var view: EditUserContract.View,
    private val userRepository: UserRepository
) : EditUserContract.Presenter {

    override fun handleEditUser(
        name: String,
        height: String,
        weight: String,
        password: String,
        confirmPassword: String
    ) {
        if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || password.isEmpty()) {
            view.showErrorEdit(Resources.getSystem().getString(R.string.error_edit_user))
            return
        }
        if (!password.contentEquals(confirmPassword)) {
            view.showErrorEdit(Resources.getSystem().getString(R.string.error_edit_user_confirm_pass))
            return
        }

        val user = userRepository.getCurrentUser()?.apply {
            this.name = name
            this.password = password
            this.height = height
            this.weight = weight
        }

        return userRepository.updateUser(
            user,
            object : OnLoadedDataCallback<Boolean?> {
                override fun onSuccessful(data: Boolean?) {
                    userRepository.editUserOnSharedPref(name)
                    view.changeToHomeScreen()
                }

                override fun onFailed(error: String) {
                    view.showErrorEdit(error)
                }
            }
        )
    }
}
