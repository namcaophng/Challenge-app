package com.sunasterisk.a14day_challenge.data.model

data class Exercise(
    val run: Double,
    val plank: Double,
    val pushUp: Double
) {
    constructor(data: MutableList<Double>) : this(
        data[0],
        data[1],
        data[2]
    )
}
