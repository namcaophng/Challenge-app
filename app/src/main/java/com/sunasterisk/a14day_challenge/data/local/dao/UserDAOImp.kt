package com.sunasterisk.a14day_challenge.data.local.dao

import android.content.SharedPreferences
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.LoadDataAsync
import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import com.sunasterisk.a14day_challenge.data.model.Exercise
import com.sunasterisk.a14day_challenge.data.model.User

class UserDAOImp private constructor(
    private val db: DataBaseHandler?,
    private val sharedPreferences: SharedPreferences
) : UserDAO {

    override fun resetStatusAllExercise() {
        sharedPreferences.edit().apply {
            putBoolean(PREF_RUN, false)
            putBoolean(PREF_PUSH_UP, false)
            putBoolean(PREF_PLANK, false)
            apply()
        }
    }

    override fun saveDoneForPlankExercise() {
        sharedPreferences.edit().apply {
            putBoolean(PREF_PLANK, true)
            apply()
        }
    }

    override fun saveDoneForPushUpExercise() {
        sharedPreferences.edit().apply {
            putBoolean(PREF_PUSH_UP, true)
            apply()
        }
    }

    override fun saveDoneForRunExercise() {
        sharedPreferences.edit().apply {
            putBoolean(PREF_RUN, true)
            apply()
        }
    }

    override fun getDataForCurrentDay(process: Int): Exercise? {
        return db?.getDataForCurrentDay(process)
    }

    override fun editUserOnSharedPref(name: String) {
        sharedPreferences.edit().apply {
            putString(PREF_NAME, name)
            apply()
        }
    }

    override fun getCurrentUser(): User? {
        val account = sharedPreferences.getString(PREF_ACCOUNT, null)
        return account?.let {
            db?.getCurrentUser(it)
        }
    }

    override fun updateUser(user: User?, callback: OnLoadedDataCallback<Boolean?>) {
        LoadDataAsync<User, Boolean?>(callback) {
            db?.updateUser(user)
        }.execute(user)
    }

    override fun getAllUsers(): List<User>? {
        return db?.getUserAll()
    }

    override fun addUser(user: User, callback: OnLoadedDataCallback<Boolean?>) {
        LoadDataAsync<User, Boolean?>(callback) {
            db?.addUser(user)
        }.execute(user)
    }

    override fun saveUserOnSharedPref(
        account: String,
        name: String,
        process: Int
    ) {
        sharedPreferences.edit().apply {
            putString(PREF_ACCOUNT, account)
            putString(PREF_NAME, name)
            putInt(PREF_PROCESS, process)
            apply()
        }
    }

    override fun saveCurrentDate(date: String) {
        sharedPreferences.edit().apply {
            putString(PREF_DATE, date)
            apply()
        }
    }

    override fun saveProcessUser(currentDay: CurrentDay) {
        sharedPreferences.edit().apply {
            putBoolean(PREF_RUN, currentDay.run)
            putBoolean(PREF_PUSH_UP, currentDay.pushedUp)
            putBoolean(PREF_PLANK, currentDay.planked)
            apply()
        }
    }

    override fun getSavedDate() = sharedPreferences.getString(PREF_DATE, null)

    override fun getProcessUser(user: User?) = CurrentDay(user, sharedPreferences)

    companion object {
        private const val PREF_ACCOUNT = "PREF_ACCOUNT"
        private const val PREF_NAME = "PREF_NAME"
        private const val PREF_PROCESS = "PREF_PROCESS"
        private const val PREF_DATE = "PREF_DATE"
        private const val PREF_RUN = "PREF_RUN"
        private const val PREF_PUSH_UP = "PREF_PUSH_UP"
        private const val PREF_PLANK = "PREF_PLANK"

        private var instance: UserDAOImp? = null

        fun getInstance(db: DataBaseHandler?, sharedPreferences: SharedPreferences) =
            instance ?: UserDAOImp(db, sharedPreferences).also { instance = it }
    }
}
