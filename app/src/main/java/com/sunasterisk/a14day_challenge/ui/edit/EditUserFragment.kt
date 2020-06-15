package com.sunasterisk.a14day_challenge.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.UserRepository
import com.sunasterisk.a14day_challenge.data.local.DataBaseHandler
import com.sunasterisk.a14day_challenge.data.local.UserLocalDataSource
import com.sunasterisk.a14day_challenge.data.local.dao.UserDAOImp
import com.sunasterisk.a14day_challenge.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_edit_user.*
import kotlinx.android.synthetic.main.fragment_edit_user.view.*

class EditUserFragment private constructor() : Fragment(),
    EditUserContract.View,
    View.OnClickListener {

    private var presenter: EditUserContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_user, container, false)
        registerListeners(view)
        return view
    }

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

        presenter = EditUserPresenter(this, userRepository)
    }

    private fun registerListeners(view: View) {
        view.buttonEditUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonEditUser -> handleEditUser()
        }
    }

    private fun handleEditUser() {
        presenter?.handleEditUser(
            editCurrentName.text.toString(),
            editCurrentHeight.text.toString(),
            editCurrentWeight.text.toString(),
            editCurrentPassword.text.toString(),
            editCurrentConfirmPassword.text.toString()
        )
    }

    override fun changeToHomeScreen() {
        val fragmentTrans = fragmentManager?.beginTransaction()
        fragmentTrans?.run {
            replace(R.id.fragment_container, HomeFragment.newInstance())
            commit()
        }
    }

    override fun showErrorEdit(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = EditUserFragment()
        private const val PREF_FILE = "PREF_FILE"
    }
}
