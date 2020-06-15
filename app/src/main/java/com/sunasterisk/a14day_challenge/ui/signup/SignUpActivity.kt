package com.sunasterisk.a14day_challenge.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.ui.ContextExtensions.Companion.showToast
import com.sunasterisk.a14day_challenge.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener,
    SignUpContract.View {

    private var presenter: SignUpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initPresenter()
        registerListeners()
    }

    private fun initPresenter() {
        val sharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(this)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val userRepository = UserRepository.getInstance(userDataSource)

        presenter = SignUpPresenter(this, userRepository)
    }

    private fun registerListeners() {
        buttonSignUpScreen.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignUpScreen -> handleSignUp()
        }
    }

    private fun handleSignUp() {
        presenter?.handleSignUp(
            editConfirmPassword.text.toString(),
            editUsername.text.toString(),
            editName.text.toString(),
            editPasswordSignUpScreen.text.toString(),
            editBirthday.text.toString(),
            editHeight.text.toString(),
            editWeight.text.toString()
        )
    }

    override fun changeToLoginScreen() {
        startActivity(LoginActivity.getIntent(this))
    }

    override fun showErrorSignUp(error: String) {
        showToast(error)
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"
    }
}
