package com.utn.apputnerds.fragments

import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.utn.apputnerds.R
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.viewmodels.ForgottenPasswordViewModel

class ForgottenPassword : Fragment() {

    companion object {
        fun newInstance() = ForgottenPassword()
    }

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null

    private lateinit var viewModel: ForgottenPasswordViewModel
    private var PREF_NAME = "UICare"
    lateinit var v: View

    lateinit var password: EditText
    lateinit var user: EditText
    lateinit var adminPassword: EditText
    lateinit var adminUser: EditText

    lateinit var btnUpdatePassword: Button

    lateinit var mp: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.forgotten_password_fragment, container, false)

        user = v.findViewById(R.id.txtName)
        password = v.findViewById(R.id.txtPassword)
        adminUser = v.findViewById(R.id.txtAdminUser)
        adminPassword = v.findViewById(R.id.txtAdminPass)

        btnUpdatePassword = v.findViewById(R.id.btnUpdatePassword)

        mp = MediaPlayer.create(requireActivity().applicationContext,R.raw.click)

        return v
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()


        btnUpdatePassword.setOnClickListener {

            if (prefs.getBoolean("sound",false)) {

                mp.start()

            }

            if ((user.text.toString() != null) && (password.text.toString() != null) && (adminPassword.text.toString() == "Admin") && (adminUser.text.toString() == "Admin") && (doctorDao?.isDoctorAvailable(user.text.toString(), user.text.toString()) == 1)) {
                var doctor = doctorDao?.loadDoctorByName(user.text.toString())
                doctor?.password = password.text.toString()
                doctorDao?.updateDoctor(doctor)
                Snackbar.make(v,"Actualizacion de Contraseña exitosa", Snackbar.LENGTH_SHORT).show()
                user.setText("")
                password.setText("")
                adminPassword.setText("")
                adminUser.setText("")
            }
            else {
                Snackbar.make(v,"Actualizacion incorrecta, verifique sus datos", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgottenPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}