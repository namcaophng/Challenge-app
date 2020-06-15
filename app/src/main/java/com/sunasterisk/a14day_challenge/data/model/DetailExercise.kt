package com.sunasterisk.a14day_challenge.data.model

import android.graphics.drawable.Drawable

data class DetailExercise(
    val image: Int,
    val title: String,
    val subTitle: String,
    val isDone: Boolean = false
)
