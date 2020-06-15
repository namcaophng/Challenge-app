package com.sunasterisk.a14day_challenge.data.local

import android.content.res.Resources
import android.os.AsyncTask
import com.sunasterisk.a14day_challenge.R
import java.lang.Exception

class LoadDataAsync<P, T>(
    private val callback: OnLoadedDataCallback<T>,
    private val function: (P) -> T
) : AsyncTask<P, Void, T?>() {
    private var exception: Exception? = null
    override fun doInBackground(vararg params: P): T? =
        try {
            function(params[0]) ?: throw Exception()
        } catch (e: Exception) {
            exception = e
            null
        }


    override fun onPostExecute(result: T?) {
        if (result == true) {
            callback.onSuccessful(result)
        } else {
//            callback.onFailed(Resources.getSystem().getString(R.string.error_load_data))
            callback.onFailed(exception.toString())
        }
    }
}
