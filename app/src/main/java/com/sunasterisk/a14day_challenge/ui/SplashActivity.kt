package com.sunasterisk.a14day_challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunasterisk.a14day_challenge.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(LoginActivity.getIntent(this))

        finish()
    }
}
