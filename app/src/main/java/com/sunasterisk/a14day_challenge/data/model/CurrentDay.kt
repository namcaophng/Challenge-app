package com.sunasterisk.a14day_challenge.data.model

import android.content.SharedPreferences

data class CurrentDay(
    val user: User?,
    var run: Boolean = false,
    var planked: Boolean = false,
    var pushedUp: Boolean = false
) {
    constructor(user: User?, sharedPreferences: SharedPreferences) : this(
        user,
        sharedPreferences.getBoolean(PREF_RUN, false),
        sharedPreferences.getBoolean(PREF_PLANK, false),
        sharedPreferences.getBoolean(PREF_PUSH_UP, false)
    )

    companion object {
        private const val PREF_RUN = "PREF_RUN"
        private const val PREF_PUSH_UP = "PREF_PUSH_UP"
        private const val PREF_PLANK = "PREF_PLANK"
    }
}
