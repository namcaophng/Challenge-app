package com.sunasterisk.a14day_challenge.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.ui.edit.EditUserFragment
import com.sunasterisk.a14day_challenge.ui.listExercise.ListExercisesActivity
import com.sunasterisk.a14day_challenge.ui.login.LoginActivity
import com.sunasterisk.a14day_challenge.ui.process.ProcessActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment private constructor() : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        registerListeners(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun registerListeners(view: View) {
        view.buttonContinueChallenge.setOnClickListener(this)
        view.buttonMyProcess.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContinueChallenge -> changeToListExerciseScreen()
            R.id.buttonMyProcess -> changeToProcessScreen()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.buttonSignOut -> changeToLoginScreen()
            R.id.buttonEditInfo -> changeToEditScreen()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun changeToListExerciseScreen() {
        context?.let {
            startActivity(ListExercisesActivity.getIntent(it))
        }
    }

    private fun changeToProcessScreen() {
        context?.let {
            startActivity(ProcessActivity.getIntent(it))
        }
    }

    private fun changeToLoginScreen() {
        context?.let {
            startActivity(LoginActivity.getIntent(it))
        }
    }

    private fun changeToEditScreen() {
        val fragmentTrans = fragmentManager?.beginTransaction()
        fragmentTrans?.run {
            replace(R.id.fragment_container, EditUserFragment.newInstance())
            commit()
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
