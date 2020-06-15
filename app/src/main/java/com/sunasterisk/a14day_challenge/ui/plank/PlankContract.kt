package com.sunasterisk.a14day_challenge.ui.plank

interface PlankContract {
    interface View {
        fun showDataForCurrentDay(timeSecond: Int)

        fun navigateToListExercise()

        fun showToast(mess: String)
    }

    interface Presenter {
        fun getDataForCurrentDay()

        fun finishPlankExercise()
    }
}
