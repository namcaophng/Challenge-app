package com.sunasterisk.a14day_challenge.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.data.model.User
import com.sunasterisk.a14day_challenge.ui.ContextExtensions.Companion.showToast
import com.sunasterisk.a14day_challenge.ui.main.MainActivity
import com.sunasterisk.a14day_challenge.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener,
    LoginContract.View {

    private var presenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initPresenter()
        registerListeners()
    }

    private fun initPresenter() {
        val sharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(this)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val userRepository = UserRepository.getInstance(userDataSource)

        presenter = LoginPresenter(this, userRepository)
    }

    private fun registerListeners() {
        buttonSignUp.setOnClickListener(this)
        buttonSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignIn -> handleLogin()
            R.id.buttonSignUp -> moveToSignUpScreen()
        }
    }

    private fun moveToSignUpScreen() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun handleLogin() {
        presenter?.handleLogin(
            editAccount.text.toString(),
            editPassword.text.toString()
        )
    }

    override fun changeToHomeScreen(user: User) {
        startActivity(MainActivity.getIntent(this, user))
    }

    override fun showErrorLogin(error: String) {
        showToast(error)
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
