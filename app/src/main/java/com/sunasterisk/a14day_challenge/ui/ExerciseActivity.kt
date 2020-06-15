package com.sunasterisk.a14day_challenge.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.ui.plank.PlankFragment
import com.sunasterisk.a14day_challenge.ui.pushUp.PushUpFragment
import com.sunasterisk.a14day_challenge.ui.run.RunFragment
import com.sunasterisk.a14day_challenge.ui.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity(), View.OnClickListener {

    private var typeExercise: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        typeExercise = intent.getStringExtra(EXTRA_TYPE_EXERCISE) ?: return
        textExerciseTitle.text = typeExercise
        buttonHowToDo.setOnClickListener(this)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_exercise_container, getFragment(typeExercise))
            .commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonHowToDo -> startActivity(TutorialActivity.getIntent(this, typeExercise))
        }
    }

    private fun getFragment(typeExercise: String): Fragment {
        var fragment: Fragment = PushUpFragment.newInstance()
        when (typeExercise) {
            getString(R.string.title_run_exercise) -> {
                fragment = RunFragment.newInstance()
            }
            getString(R.string.title_plank_exercise) -> {
                fragment = PlankFragment.newInstance()
            }
        }
        return fragment
    }

    companion object {
        const val EXTRA_TYPE_EXERCISE = "EXTRA_TYPE_EXERCISE"

        fun getIntent(context: Context, type: String) =
            Intent(context, ExerciseActivity::class.java).apply {
                putExtra(EXTRA_TYPE_EXERCISE, type)
            }
    }
}
