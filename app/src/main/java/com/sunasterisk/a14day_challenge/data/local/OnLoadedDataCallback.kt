package com.sunasterisk.a14day_challenge.data.local

interface OnLoadedDataCallback<T> {
    fun onSuccessful(data: T)
    fun onFailed(error: String)
}
