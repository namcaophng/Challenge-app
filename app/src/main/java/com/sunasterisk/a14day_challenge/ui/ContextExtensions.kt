package com.sunasterisk.a14day_challenge.ui

import android.content.Context
import android.widget.Toast

class ContextExtensions {
    companion object {
        fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, message, duration).show()
        }

        fun Context.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, this.getString(resId), duration).show()
        }
    }
}
