package com.sunasterisk.a14day_challenge.data.model

import android.content.ContentValues
import android.database.Cursor

data class User(
    var account: String,
    var name: String,
    var password: String,
    var birthday: String,
    var height: String,
    var weight: String,
    var process: Int
) {
    constructor(cursor: Cursor) : this(
        cursor.getString(cursor.getColumnIndex(USER_ACC)),
        cursor.getString(cursor.getColumnIndex(USER_NAME)),
        cursor.getString(cursor.getColumnIndex(USER_PASS)),
        cursor.getString(cursor.getColumnIndex(USER_BIR)),
        cursor.getString(cursor.getColumnIndex(USER_HEIGHT)),
        cursor.getString(cursor.getColumnIndex(USER_WEIGHT)),
        cursor.getInt(cursor.getColumnIndex(USER_PROCESS))
    )

    fun getContentValues() = ContentValues().apply {
        put(USER_ACC, account)
        put(USER_NAME, name)
        put(USER_PASS, password)
        put(USER_BIR, birthday)
        put(USER_HEIGHT, height)
        put(USER_WEIGHT, weight)
        put(USER_PROCESS, process)
    }

    companion object {
        private const val USER_ACC = "account"
        private const val USER_NAME = "name"
        private const val USER_PASS = "password"
        private const val USER_BIR = "birthday"
        private const val USER_HEIGHT = "height"
        private const val USER_WEIGHT = "weight"
        private const val USER_PROCESS = "process"
    }
}
