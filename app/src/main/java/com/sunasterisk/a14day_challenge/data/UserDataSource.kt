package com.sunasterisk.a14day_challenge.data

import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import com.sunasterisk.a14day_challenge.data.model.Exercise
import com.sunasterisk.a14day_challenge.data.model.User

interface UserDataSource {
    interface Local {
        fun getAllUsers(): List<User>?

        fun addUser(user: User, callback: OnLoadedDataCallback<Boolean?>)

        fun saveUserOnSharedPref(account: String, name: String, process: Int)

        fun saveCurrentDate(date: String)

        fun saveProcessUser(currentDay: CurrentDay)

        fun getProcessUser(user: User?): CurrentDay

        fun getSavedDate(): String?

        fun getCurrentUser(): User?

        fun updateUser(user: User?, callback: OnLoadedDataCallback<Boolean?>)

        fun editUserOnSharedPref(name: String)

        fun getDataForCurrentDay(process: Int): Exercise?

        fun saveDoneForRunExercise()

        fun saveDoneForPushUpExercise()

        fun saveDoneForPlankExercise()

        fun resetStatusAllExercise()
    }
}
