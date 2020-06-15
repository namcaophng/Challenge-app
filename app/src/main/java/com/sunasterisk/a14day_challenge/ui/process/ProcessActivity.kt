package com.sunasterisk.a14day_challenge.ui.process

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.adapter.ProcessAdapter
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.data.model.User
import com.sunasterisk.a14day_challenge.ui.ContextExtensions.Companion.showToast
import com.sunasterisk.a14day_challenge.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_process.*

class ProcessActivity : AppCompatActivity(), ProcessContract.View {

    private var presenter: ProcessPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)

        initPresenter()
        initData()
    }

    private fun initPresenter() {
        val sharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(this)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val userRepository = UserRepository.getInstance(userDataSource)

        presenter = ProcessPresenter(this, userRepository)
    }

    private fun initData() {
        presenter?.getProgresses()
    }

    override fun showDays(user: User, processList: List<String>) {
        val viewManager = LinearLayoutManager(this)
        viewManager.scrollToPosition(0)
        val viewAdapter = ProcessAdapter(processList)
        textNumberDay.text = getTextNumberDays(user.process - 1)
        textName.text = user.name
        textHeightAndWeight.text = getTextHeightAndWeight(user.height, user.weight)

        recycleViewProcess.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun getTextHeightAndWeight(height: String, weight: String): String {
        return "${height}cm - ${weight}kg"
    }

    private fun getTextNumberDays(day: Int): String {
        return "$day days"
    }

    override fun navigateToLoginScreen() {
        showToast(getString(R.string.error_load_recyclerview))
        startActivity(LoginActivity.getIntent(this))
    }


    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun getIntent(context: Context): Intent = Intent(context, ProcessActivity::class.java)
    }
}
