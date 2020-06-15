package com.sunasterisk.a14day_challenge.ui.listExercise

import android.util.Log
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListExercisesPresenter(
    private val view: ListExercisesContract.View,
    private val repository: UserRepository
) : ListExercisesContract.Presenter {

    override fun initProgressCurrentDay() {
        val user = repository.getCurrentUser()
        val savedDate = repository.getSavedDate()
        val tempLocalDate = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)

        if (savedDate == null || !savedDate.contentEquals(tempLocalDate)) {
            repository.run {
                saveCurrentDate(tempLocalDate)
                saveProcessUser(CurrentDay(user))
            }
        }

        view.showDataOnRecyclerView(repository.getProcessUser(user))
    }
}
