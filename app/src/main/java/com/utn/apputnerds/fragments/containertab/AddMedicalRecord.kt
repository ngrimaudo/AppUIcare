package com.utn.apputnerds.fragments.containertab

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
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
import com.utn.apputnerds.database.medicalrecordDao
import com.utn.apputnerds.database.patientDao
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.entities.MedicalRecord
import com.utn.apputnerds.entities.Patient
import com.utn.apputnerds.viewmodels.AddMedicalRecordViewModel
import java.util.*

class AddMedicalRecord : Fragment() {

    companion object {
        fun newInstance() = AddMedicalRecord()
    }

    private lateinit var viewModel: AddMedicalRecordViewModel

    private var db: appDatabase? = null
    private var doctorDao: doctorDao? = null
    private var patientDao: patientDao? = null
    private var medicalrecordDao: medicalrecordDao? = null

    private var PREF_NAME = "UICare"
    lateinit var v: View

    lateinit var diagnosis: EditText
    lateinit var treatment: EditText

    lateinit var btnAddMedicalrecord: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.add_medical_record_fragment, container, false)

        diagnosis = v.findViewById(R.id.txtDiagnosis)
        treatment = v.findViewById(R.id.txtTreatment)

        btnAddMedicalrecord = v.findViewById(R.id.btnAddMedicalRecord)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        doctorDao = db?.doctorDao()
        patientDao = db?.patientDao()
        medicalrecordDao = db?.medicalrecordDao()

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var id = sharedPref.getInt("id", -1)
        var patient = patientDao?.loadPatientById(id) as Patient


        btnAddMedicalrecord.setOnClickListener {



            if ((diagnosis.text.toString() != null) && (treatment.text.toString() != null)) {
                var newMedicalRecord = MedicalRecord(SimpleDateFormat("dd/mm/yyyy").format(Calendar.getInstance().getTime()), patient.name, diagnosis.text.toString(), treatment.text.toString())
                val u = medicalrecordDao?.insertMedicalRecord(newMedicalRecord)
                Snackbar.make(v,"Creacion exitosa", Snackbar.LENGTH_SHORT).show()
                diagnosis.setText("")
                treatment.setText("")
            }
            else {
                Snackbar.make(v,"Creacion incorrecta", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMedicalRecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}