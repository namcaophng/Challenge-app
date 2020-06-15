package com.sunasterisk.a14day_challenge.ui.process

import com.sunasterisk.a14day_challenge.data.UserRepository

class ProcessPresenter(
    private val view: ProcessContract.View,
    private val repository: UserRepository
) : ProcessContract.Presenter {

    override fun getProgresses() {
        val user = repository.getCurrentUser()

        if (user == null) {
            view.navigateToLoginScreen()
            return
        }

        val processList = (1..user.process).map {
            "Day $it: "
        }
        view.showDays(user, processList)
    }
}
