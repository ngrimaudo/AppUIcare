package com.utn.apputnerds.fragments

import android.content.Context
import android.content.SharedPreferences
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
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.entities.Patient
import com.utn.apputnerds.viewmodels.AddPatientViewModel

class AddPatientFragment : Fragment() {

    companion object {
        fun newInstance() = AddPatientFragment()
    }

    private lateinit var viewModel: AddPatientViewModel

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null

    private var PREF_NAME = "UICare"
    lateinit var v: View

    lateinit var edad: EditText
    lateinit var name: EditText
    lateinit var lastname: EditText

    lateinit var btnAddPatient: Button

    lateinit var mp: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.add_patient_fragment, container, false)

        edad = v.findViewById(R.id.txtEdad)
        lastname = v.findViewById(R.id.txtLastName)
        name = v.findViewById(R.id.txtName)

        btnAddPatient = v.findViewById(R.id.btnAddPatient)

        mp = MediaPlayer.create(requireActivity().applicationContext,R.raw.click)

        return v
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()
        patientDao = db?.patientDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = sharedPref.getInt("idDoctor", -1)
        var doctor = doctorDao?.loadDoctorById(id) as Doctor


        btnAddPatient.setOnClickListener {

            if (prefs.getBoolean("sound",false)) {

                mp.start()

            }

            val patient = patientDao?.validate(name.text.toString(), doctor.name)
            if (patient == null && (name.text.toString() != null) && (edad.text.toString() != null) && (lastname.text.toString() != null)) {
                var newPatient = Patient(name.text.toString(), lastname.text.toString(), edad.text.toString().toInt(), "  ", doctor.name)
                val u = patientDao?.insertPatient(newPatient)
                Snackbar.make(v,"Creacion exitosa", Snackbar.LENGTH_SHORT).show()
                name.setText("")
                lastname.setText("")
                edad.setText("")
            }
            else {
                Snackbar.make(v,"Creacion incorrecta", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)
        // TODO: Use the ViewModel
    }

}