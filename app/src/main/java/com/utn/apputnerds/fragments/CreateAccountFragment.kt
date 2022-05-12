package com.utn.apputnerds.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.utn.apputnerds.R
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.viewmodels.CreateAccountViewModel

class CreateAccountFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAccountFragment()
    }

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null

    private lateinit var viewModel: CreateAccountViewModel
    private var PREF_NAME = "UICare"
    lateinit var v: View

    lateinit var password: EditText
    lateinit var user: EditText
    lateinit var mail: EditText
    lateinit var speciality: EditText

    lateinit var btnCreateDoctor: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.create_account_fragment, container, false)

        user = v.findViewById(R.id.txtName)
        password = v.findViewById(R.id.txtPassword)
        mail = v.findViewById(R.id.txtMail)
        speciality = v.findViewById(R.id.txtSpeciality)

        btnCreateDoctor = v.findViewById(R.id.btnCreateDoctor)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()


        btnCreateDoctor.setOnClickListener {



            val username = doctorDao?.validate(user.text.toString(), password.text.toString())
            if (username == null && (user.text.toString() != null) && (password.text.toString() != null) && (mail.text.toString() != null) && (speciality.text.toString() != null)) {
                var newDoctor = Doctor(user.text.toString(),password.text.toString(), mail.text.toString(), speciality.text.toString())
                val u = doctorDao?.insertDoctor(newDoctor)
                Snackbar.make(v,"Creacion exitosa", Snackbar.LENGTH_SHORT).show()
                user.setText("")
                password.setText("")
                mail.setText("")
                speciality.setText("")
            }
            else {
                Snackbar.make(v,"Creacion incorrecta", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}