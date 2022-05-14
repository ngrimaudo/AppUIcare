package com.utn.apputnerds.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.apputnerds.R
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.viewmodels.UserInfoViewModel

class DoctorInfoFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorInfoFragment()
    }

    private lateinit var viewModel: UserInfoViewModel
    private var PREF_NAME = "UICare"
    lateinit var v: View

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null

    lateinit var speciality: TextView
    lateinit var name: TextView
    lateinit var mail: TextView
    lateinit var countPatient: TextView
    lateinit var btnSettings: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.doctor_info_fragment, container, false)

        name = v.findViewById(R.id.txtName)
        speciality = v.findViewById(R.id.txtSpeciality)
        mail = v.findViewById(R.id.txtSelfie)
        countPatient = v.findViewById(R.id.txtCountPatient)

        btnSettings = v.findViewById(R.id.btnSettings)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()
        patientDao = db?.patientDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = sharedPref.getInt("idDoctor", -1)
        var doctor = doctorDao?.loadDoctorById(id) as Doctor
        var qtyPatient = patientDao?.qtyPatientByDoctor(doctor.name) as Int

        name.text = "${doctor.name}"
        mail.text = "${doctor.mail}"
        speciality.text = "${doctor.speciality}"
        countPatient.text = "$qtyPatient"


        btnSettings.setOnClickListener(){
            val action = DoctorInfoFragmentDirections.actionUserInfoToSettingsActivity()
            v.findNavController().navigate(action)
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}