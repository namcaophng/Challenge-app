package com.sunasterisk.a14day_challenge.ui.main

interface MainContract {

    interface View {
        fun showToastError(error: String)
    }

    interface Presenter {
        fun saveUserInSharedPref(
            account: String?,
            name: String?,
            process: Int
        )
    }
}
