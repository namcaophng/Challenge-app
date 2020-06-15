package com.sunasterisk.a14day_challenge.ui.process

import com.sunasterisk.a14day_challenge.data.model.User

interface ProcessContract {

    interface View {
        fun showDays(user: User, processList: List<String>)
        fun navigateToLoginScreen()
    }

    interface Presenter {
        fun getProgresses()
    }
}
