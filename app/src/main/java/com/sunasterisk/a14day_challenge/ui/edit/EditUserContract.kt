package com.sunasterisk.a14day_challenge.ui.edit

interface EditUserContract {
    interface View {
        fun changeToHomeScreen()

        fun showErrorEdit(error: String)
    }

    interface Presenter {
        fun handleEditUser(
            name: String,
            height: String,
            weight: String,
            password: String,
            confirmPassword: String
        )
    }
}
