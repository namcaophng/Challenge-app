package com.sunasterisk.a14day_challenge.ui.main

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository

class MainPresenter(
    var view: MainContract.View,
    private val userRepository: UserRepository
) : MainContract.Presenter {

    override fun saveUserInSharedPref(
        account: String?,
        name: String?,
        process: Int
    ) {
        if (account != null && name != null) {
            userRepository.saveUserOnSharedPref(account, name, process)
        } else {
            view.showToastError(Resources.getSystem().getString(R.string.error_push_shared_pref))
        }
    }
}
