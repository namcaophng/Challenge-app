package com.sunasterisk.a14day_challenge.ui.pushUp

interface PushUpContract {
    interface View {
        fun showDataForCurrentDay(times: Int)

        fun navigateToListExercise()

        fun showToast(mess: String)
    }

    interface Presenter {
        fun getDataForCurrentDay()

        fun finishPushUpExercise()
    }
}
