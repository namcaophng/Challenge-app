package com.sunasterisk.a14day_challenge.ui.pushUp

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback

class PushUpPresenter(
    private val view: PushUpContract.View,
    private val repository: UserRepository
) : PushUpContract.Presenter {

    override fun getDataForCurrentDay() {
        val user = repository.getCurrentUser() ?: return
        val timesPushUpCurrentDay = repository.getDataForCurrentDay(user.process) ?: return

        view.showDataForCurrentDay(timesPushUpCurrentDay.pushUp.toInt())
    }

    override fun finishPushUpExercise() {
        repository.saveDoneForPushUpExercise()
        view.navigateToListExercise()
        val user = repository.getCurrentUser() ?: return
        val currentDay = repository.getProcessUser(user)
        if (currentDay.run && currentDay.planked && currentDay.pushedUp) {
            user.process += 1
            repository.updateUser(
                user,
                object : OnLoadedDataCallback<Boolean?> {
                    override fun onSuccessful(data: Boolean?) {
                        view.showToast(
                            Resources.getSystem().getString(R.string.title_completed_exercise)
                        )
                    }

                    override fun onFailed(error: String) {
                        view.showToast(error)
                    }
                })

            repository.resetStatusAllExercise()
        }
    }
}
