package com.sunasterisk.a14day_challenge.ui.plank

import android.content.res.Resources
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback

class PlankPresenter(
    private val view: PlankContract.View,
    private val repository: UserRepository
) : PlankContract.Presenter {

    override fun getDataForCurrentDay() {
        val user = repository.getCurrentUser() ?: return
        val timesPlankCurrentDay = repository.getDataForCurrentDay(user.process) ?: return

        view.showDataForCurrentDay(timesPlankCurrentDay.plank.toInt())
    }

    override fun finishPlankExercise() {
        repository.saveDoneForPlankExercise()
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
