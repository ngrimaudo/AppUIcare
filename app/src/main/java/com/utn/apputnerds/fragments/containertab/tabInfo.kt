package com.utn.apputnerds.fragments.containertab

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utn.apputnerds.R
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.entities.Patient
import com.utn.apputnerds.viewmodels.TabInfoViewModel

class tabInfo : Fragment() {

    companion object {
        fun newInstance() = tabInfo()
    }

    private lateinit var viewModel: TabInfoViewModel
    private var PREF_NAME = "UICare"
    lateinit var v: View

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null

    lateinit var lastname: TextView
    lateinit var name: TextView
    lateinit var selfie: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.tab_info_fragment, container, false)

        name = v.findViewById(R.id.txtName)
        lastname = v.findViewById(R.id.txtLastName)
        selfie = v.findViewById(R.id.txtSelfie)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()
        patientDao = db?.patientDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = sharedPref.getInt("idPatient", -1)
        var patient = patientDao?.loadPatientById(id) as Patient

        name.text = "${patient.name}"
        lastname.text = "${patient.lastName}"
        selfie.text = "${patient.urlSelfie}"

    }


        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}