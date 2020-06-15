package com.sunasterisk.a14day_challenge.data

import com.sunasterisk.a14day_challenge.data.local.OnLoadedDataCallback
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import com.sunasterisk.a14day_challenge.data.model.Exercise
import com.sunasterisk.a14day_challenge.data.model.User

class UserRepository private constructor(
    private val local: UserDataSource.Local
) : UserDataSource.Local {

    override fun resetStatusAllExercise() {
        local.resetStatusAllExercise()
    }

    override fun saveDoneForRunExercise() {
        local.saveDoneForRunExercise()
    }

    override fun saveDoneForPushUpExercise() {
        local.saveDoneForPushUpExercise()
    }

    override fun saveDoneForPlankExercise() {
        local.saveDoneForPlankExercise()
    }

    override fun getDataForCurrentDay(process: Int): Exercise? {
        return local.getDataForCurrentDay(process)
    }

    override fun editUserOnSharedPref(name: String) {
        local.editUserOnSharedPref(name)
    }

    override fun updateUser(user: User?, callback: OnLoadedDataCallback<Boolean?>) {
        local.updateUser(user, callback)
    }

    override fun getCurrentUser(): User? {
        return local.getCurrentUser()
    }

    override fun saveUserOnSharedPref(account: String, name: String, process: Int) {
        local.saveUserOnSharedPref(account, name, process)
    }

    override fun saveCurrentDate(date: String) {
        local.saveCurrentDate(date)
    }

    override fun saveProcessUser(currentDay: CurrentDay) {
        local.saveProcessUser(currentDay)
    }

    override fun getProcessUser(user: User?): CurrentDay {
        return local.getProcessUser(user)
    }

    override fun getSavedDate(): String? {
        return local.getSavedDate()
    }

    override fun addUser(user: User, callback: OnLoadedDataCallback<Boolean?>) {
        local.addUser(user, callback)
    }

    override fun getAllUsers(): List<User>? {
        return local.getAllUsers()
    }

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(local: UserDataSource.Local) =
            instance ?: UserRepository(local).also { instance = it }
    }
}
