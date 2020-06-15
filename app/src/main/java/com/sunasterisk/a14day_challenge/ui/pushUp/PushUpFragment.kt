package com.sunasterisk.a14day_challenge.ui.pushUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.ui.listExercise.ListExercisesActivity
import kotlinx.android.synthetic.main.fragment_push_up.view.*

class PushUpFragment private constructor() : Fragment(),
    PushUpContract.View,
    View.OnClickListener {

    private var presenter: PushUpPresenter? = null
    private var textTimesPushUp: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_push_up, container, false)
        registerListeners(view)
        textTimesPushUp = view.findViewById(R.id.textTitlePushUpExercise)
        presenter?.getDataForCurrentDay()
        return view
    }

    private fun registerListeners(view: View) {
        view.buttonFinishPushUp.setOnClickListener(this)
    }

    private fun initPresenter() {
        val context = context ?: return
        val sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(context)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val userRepository = UserRepository.getInstance(userDataSource)

        presenter = PushUpPresenter(this, userRepository)
    }

    override fun showDataForCurrentDay(times: Int) {
        textTimesPushUp?.text = getTextTitle(times)
    }

    override fun navigateToListExercise() {
        context?.let {
            startActivity(ListExercisesActivity.getIntent(it))
        }
    }

    override fun showToast(mess: String) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }

    private fun getTextTitle(times: Int): String = "${getString(R.string.title_suggestion_push_up)} $times"

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonFinishPushUp -> presenter?.finishPushUpExercise()
        }
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun newInstance() = PushUpFragment()
    }
}
