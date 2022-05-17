package com.utn.apputnerds.fragments

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.apputnerds.R
import com.utn.apputnerds.adapters.PatientListAdapter
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.entities.Patient
import com.utn.apputnerds.viewmodels.ListPatientViewModel

class ListPatientFragment : Fragment() {

    lateinit var v: View

    private var PREF_NAME = "UICare"
    lateinit var recPatient : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null

    private lateinit var patientListAdapter: PatientListAdapter
    private lateinit var btnAddPatient: FloatingActionButton

    lateinit var mp: MediaPlayer

    var patient : MutableList<Patient> = ArrayList<Patient>()

    companion object {
        fun newInstance() = ListPatientFragment()
    }

    private lateinit var viewModel: ListPatientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.list_patient_fragment, container, false)

        recPatient = v.findViewById(R.id.rec_patient)

        btnAddPatient = v.findViewById(R.id.btnAddPatient)

        mp = MediaPlayer.create(requireActivity().applicationContext,R.raw.click)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPatientViewModel::class.java)
        // TODO: Use the ViewModel
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

        var listPatients = patientDao?.getPatients(doctor.name)

        recPatient.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recPatient.layoutManager = linearLayoutManager

        patientListAdapter = PatientListAdapter(listPatients,requireContext()){ pos->
            onItemClick(pos)

            if (prefs.getBoolean("sound",false)) {

                mp.start()

            }


        }

//        patientListAdapter = PatientListAdapter(Patient)

        recPatient.adapter = patientListAdapter

        btnAddPatient.setOnClickListener {
            val action = ListPatientFragmentDirections.actionListPatientFragmentToAddPatientFragment()
            v.findNavController().navigate(action)
        }

    }

    fun onItemClick ( position : Int )  {

        db = appDatabase.getAppDataBase(v.context)
        patientDao = db?.patientDao()
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        var id = sharedPref.getInt("idDoctor", -1)
        var doctor = doctorDao?.loadDoctorById(id) as Doctor

        var listPatients = patientDao?.getPatients(doctor.name)

        val u = patientDao?.loadPatientById(listPatients!![position].id)
        val editor = sharedPref.edit()
        editor.putInt("idPatient", u!!.id)
        editor.apply()
        v.findNavController().navigate(ListPatientFragmentDirections.actionListPatientFragmentToContainer())
    }

}