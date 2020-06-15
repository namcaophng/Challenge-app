package com.sunasterisk.a14day_challenge.ui.run

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.service.RunExerciseService
import com.sunasterisk.a14day_challenge.ui.ContextExtensions.Companion.showToast
import com.sunasterisk.a14day_challenge.ui.listExercise.ListExercisesActivity
import kotlinx.android.synthetic.main.fragment_run.view.*

class RunFragment private constructor() : Fragment(),
    RunContract.View,
    View.OnClickListener {

    private var textActualMeterRun: TextView? = null
    private var textMeterRunForToday: TextView? = null
    private var presenter: RunPresenter? = null

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

        presenter = RunPresenter(this, userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_run, container, false)

        textActualMeterRun = view.textActualMeterRun
        textMeterRunForToday = view.textMeterRun
        registerListeners(view)
        presenter?.getDataForCurrentDay()

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonStartRunning -> {
                val cxt = context ?: return
                if (ActivityCompat.checkSelfPermission(
                        cxt,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        cxt,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    cxt.startService(Intent(context, RunExerciseService::class.java))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        context?.registerReceiver(
            broadcastReceiver,
            IntentFilter(RunExerciseService.BROADCAST_ACTION)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.unregisterReceiver(broadcastReceiver)
    }

    override fun showToast(mess: Any) {
        val message = mess.toString()
        if (message.toIntOrNull() != null) {
            context?.showToast(message.toInt())
            return
        }
        context?.showToast(message)
    }

    override fun navigateToListExercise() {
        context?.let {
            startActivity(ListExercisesActivity.getIntent(it))
        }
    }

    override fun showDataForCurrentDay(meter: Int) {
        textMeterRunForToday?.text =
            context?.getString(R.string.title_suggestion_run_exercise, meter)
    }

    private fun registerListeners(view: View) {
        view.buttonStartRunning.setOnClickListener(this)
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == RunExerciseService.BROADCAST_ACTION) return

            val distance = intent.getStringExtra("distance")?.toIntOrNull() ?: return
            textActualMeterRun?.text = context?.getString(R.string.title_update_ui, distance)

            if (distance >= textMeterRunForToday?.text.toString().toInt()) {
                context?.stopService(Intent(context, RunExerciseService::class.java))
                presenter?.finishRunExercise()
            }
        }
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun newInstance() = RunFragment()
    }
}
