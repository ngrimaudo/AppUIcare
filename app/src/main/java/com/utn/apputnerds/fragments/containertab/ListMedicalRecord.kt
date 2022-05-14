package com.utn.apputnerds.fragments.containertab

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.apputnerds.R
import com.utn.apputnerds.adapters.MedicalRecordListAdapter
import com.utn.apputnerds.database.appDatabase
import com.utn.apputnerds.database.doctorDao
import com.utn.apputnerds.database.medicalrecordDao
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.MedicalRecord
import com.utn.apputnerds.entities.Patient
import com.utn.apputnerds.viewmodels.ListMedicalRecordViewModel

class ListMedicalRecord : Fragment() {

    lateinit var v: View

    private var PREF_NAME = "UICare"
    lateinit var recMedicalRecord : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null
    private var medicalrecordDao: medicalrecordDao? = null

    private lateinit var medicalrecordListAdapter: MedicalRecordListAdapter
    private lateinit var btnAddMedicalRecord: FloatingActionButton

    var medicalrecord : MutableList<MedicalRecord> = ArrayList<MedicalRecord>()

    companion object {
        fun newInstance() = ListMedicalRecord()
    }

    private lateinit var viewModel: ListMedicalRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.list_medical_record_fragment, container, false)

        recMedicalRecord = v.findViewById(R.id.rec_medicalrecord)

        btnAddMedicalRecord = v.findViewById(R.id.btnAddMedicalRecord)


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListMedicalRecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()
        patientDao = db?.patientDao()
        medicalrecordDao = db?.medicalrecordDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = sharedPref.getInt("idPatient", -1)
        var patient = patientDao?.loadPatientById(id) as Patient

        var listMedicalRecord = medicalrecordDao?.selectMedicalRecordByPatient(patient.name)

        recMedicalRecord.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recMedicalRecord.layoutManager = linearLayoutManager

        medicalrecordListAdapter = MedicalRecordListAdapter(listMedicalRecord,requireContext()){ pos->
            onItemClick(pos)


        }

//        patientListAdapter = PatientListAdapter(Patient)

        recMedicalRecord.adapter = medicalrecordListAdapter

        btnAddMedicalRecord.setOnClickListener {

            val action = containerDirections.actionContainerToAddMedicalRecord()
            v.findNavController().navigate(action)
        }

    }

    fun onItemClick ( position : Int )  {

        Snackbar.make(v,"Historia Clinica del Paciente", Snackbar.LENGTH_SHORT).show()

    }

}