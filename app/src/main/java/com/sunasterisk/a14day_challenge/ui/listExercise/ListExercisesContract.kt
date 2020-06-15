package com.sunasterisk.a14day_challenge.ui.listExercise

import android.database.CursorIndexOutOfBoundsException
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import com.sunasterisk.a14day_challenge.data.model.DetailExercise

interface ListExercisesContract {

    interface View {
        fun showDataOnRecyclerView(currentDay: CurrentDay)

        fun navigateToExercise(type: String)
    }

    interface Presenter {
        fun initProgressCurrentDay()
    }
}
