package com.sunasterisk.a14day_challenge.ui.plank

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.ui.listExercise.ListExercisesActivity
import kotlinx.android.synthetic.main.fragment_plank.view.*


class PlankFragment private constructor() : Fragment(),
    PlankContract.View,
    View.OnClickListener {

    private var progressTime: ProgressBar? = null
    private var textTime: TextView? = null
    private var presenter: PlankPresenter? = null
    private var countDownTimer: CountDownPlankTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
    }

    private fun initPresenter() {
        val context = context ?: return
        val sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(context)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val userRepository = UserRepository.getInstance(userDataSource)

        presenter = PlankPresenter(this, userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plank, container, false)

        progressTime = view.findViewById(R.id.progressTime)
        textTime = view.findViewById(R.id.textTimePlank)
        registerListeners(view)
        presenter?.getDataForCurrentDay()

        return view
    }

    private fun registerListeners(view: View) {
        view.buttonStartPlank.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonStartPlank -> startCountDownTime()
        }
    }

    override fun showDataForCurrentDay(timeSecond: Int) {
        textTime?.text = timeSecond.toString()
    }

    override fun navigateToListExercise() {
        context?.let {
            startActivity(ListExercisesActivity.getIntent(it))
        }
    }

    override fun showToast(mess: String) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }

    private fun startCountDownTime() {
        countDownTimer?.cancel()
        countDownTimer = null

        val time = textTime?.text.toString().toLong() * 1000
        progressTime?.max = time.toInt()
        countDownTimer = CountDownPlankTimer(time, 100)
        countDownTimer?.start()
    }

    inner class CountDownPlankTimer(
        millisInFuture: Long,
        countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            val timeSecond = millisUntilFinished / 1000
            textTime?.text = timeSecond.toString()
            val progress = millisUntilFinished.toInt()
            progressTime?.progress = progress
        }

        override fun onFinish() {
            textTime?.text = getString(R.string.title_finish_task)
            progressTime?.progress = 0
            presenter?.finishPlankExercise()
        }
    }

    override fun onStop() {
        super.onStop()
        countDownTimer?.cancel()
        countDownTimer = null
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun newInstance() = PlankFragment()
    }
}
