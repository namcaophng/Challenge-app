package com.sunasterisk.a14day_challenge.ui.run

import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback

class RunPresenter(
    private val view: RunContract.View,
    private val repository: UserRepository
) : RunContract.Presenter {
    override fun getDataForCurrentDay() {
        val user = repository.getCurrentUser() ?: return
        val meterRunForToday = repository.getDataForCurrentDay(user.process) ?: return

        view.showDataForCurrentDay((meterRunForToday.run * conversionKilometerToMeter).toInt())
    }

    override fun finishRunExercise() {
        repository.saveDoneForRunExercise()
        view.navigateToListExercise()

        val user = repository.getCurrentUser() ?: return
        val currentDay = repository.getProcessUser(user)

        if (currentDay.run && currentDay.planked && currentDay.pushedUp) {
            user.process += 1
            repository.updateUser(
                user,
                object : OnLoadedDataCallback<Boolean?> {

                    override fun onSuccessful(data: Boolean?) {
                        view.showToast(R.string.title_completed_exercise)
                    }

                    override fun onFailed(error: String) {
                        view.showToast(error)
                    }
                })

            repository.resetStatusAllExercise()
        }
    }

    companion object {
        private const val conversionKilometerToMeter = 1000
    }
}
