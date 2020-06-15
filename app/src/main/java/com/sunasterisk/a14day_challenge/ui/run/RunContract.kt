package com.sunasterisk.a14day_challenge.ui.run

interface RunContract {
    interface View {

        fun showToast(mess: Any)

        fun navigateToListExercise()

        fun showDataForCurrentDay(meter: Int)
    }

    interface Presenter {
        fun getDataForCurrentDay()

        fun finishRunExercise()
    }
}
