package com.sunasterisk.a14day_challenge.ui.listExercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.adapter.DetailExercisesAdapter
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.data.model.CurrentDay
import com.sunasterisk.a14day_challenge.data.model.DetailExercise
import com.sunasterisk.a14day_challenge.ui.ExerciseActivity
import kotlinx.android.synthetic.main.activity_list_exercises.*

class ListExercisesActivity : AppCompatActivity(), ListExercisesContract.View {

    private var presenter: ListExercisesPresenter? = null
    private val detailExerciseList = mutableListOf<DetailExercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exercises)

        initPresenter()
        presenter?.initProgressCurrentDay()
    }

    private fun initPresenter() {
        val sharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val db = DataBaseHandler.getInstance(this)
        val userDAO = UserDAOImp.getInstance(db, sharedPreferences)
        val userDataSource = UserLocalDataSource.getInstance(userDAO)
        val repository = UserRepository.getInstance(userDataSource)

        presenter = ListExercisesPresenter(this, repository)
    }

    override fun showDataOnRecyclerView(currentDay: CurrentDay) {
        detailExerciseList.addAll(getDetailExerciseList(currentDay))
        val viewManager = LinearLayoutManager(this)
        viewManager.scrollToPosition(0)
        val viewAdapter =
            DetailExercisesAdapter(detailExerciseList) { title -> onExerciseClick(title) }

        recycleViewListExercises.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun getDetailExerciseList(currentDay: CurrentDay) = mutableListOf(
        DetailExercise(
            R.drawable.image_push_up,
            getString(R.string.title_push_up_exercise),
            getString(R.string.title_short_guide_push_up),
            currentDay.pushedUp
        ),
        DetailExercise(
            R.drawable.image_plank,
            getString(R.string.title_plank_exercise),
            getString(R.string.title_short_guide_plank),
            currentDay.planked
        ),
        DetailExercise(
            R.drawable.image_run,
            getString(R.string.title_run_exercise),
            getString(R.string.title_short_guide_run),
            currentDay.run
        )
    )

    private fun onExerciseClick(title: String) {
        navigateToExercise(title)
    }

    override fun navigateToExercise(type: String) {
        startActivity(ExerciseActivity.getIntent(this, type))
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        fun getIntent(context: Context): Intent = Intent(context, ListExercisesActivity::class.java)
    }
}
