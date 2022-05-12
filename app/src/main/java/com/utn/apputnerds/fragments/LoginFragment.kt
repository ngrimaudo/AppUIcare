package com.utn.apputnerds.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.apputnerds.R
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null

    private lateinit var viewModel: LoginViewModel
    private var PREF_NAME = "UICare"
    lateinit var v: View

    lateinit var btnLogin: Button
    lateinit var btnCreateAccount: Button

    lateinit var password: EditText
    lateinit var user: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)

        user = v.findViewById(R.id.txtName)
        password = v.findViewById(R.id.txtPassword)

        btnLogin = v.findViewById(R.id.btnCreateDoctor)
        btnCreateAccount = v.findViewById(R.id.btnCreateAccount)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()


        btnLogin.setOnClickListener {

            val username = doctorDao?.validate(user.text.toString(), password.text.toString())
            if (username != null) {
                val u = doctorDao?.loadDoctorByName(username)
                val action = LoginFragmentDirections.actionLoginFragmentToNavBarActivity()
                val editor = sharedPref.edit()
                editor.putInt("id", u!!.id)
                editor.apply()
                v.findNavController().navigate(action)
            }
            else {
                Snackbar.make(v,"Usuario o Contraseña incorrecto", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnCreateAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}