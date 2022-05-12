package com.utn.apputnerds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utn.apputnerds.R
import com.utn.apputnerds.entities.Patient

class PatientListAdapter (
    private var patientList: MutableList<Patient>?,
    val context: Context,
    val onItemClick : (Int) -> Unit


) : RecyclerView.Adapter<PatientListAdapter.PatientHolder>(){

    companion object {

        private val TAG = "PatientListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): PatientListAdapter.PatientHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_patient,parent,false)
        return (PatientHolder(view))

    }

    override fun onBindViewHolder(holder: PatientListAdapter.PatientHolder, position: Int) {
        holder.setName(patientList?.get(position))
        holder.setAge(patientList?.get(position))
        holder.getCardLayout().setOnClickListener  () {
            onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return patientList!!.size
    }

    class PatientHolder (v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setName(patient: Patient?) {
            val txt: TextView = view.findViewById(R.id.txtPatientName)
            txt.text = patient?.name
        }

        fun setAge(patient: Patient?) {
            val txt: TextView = view.findViewById(R.id.txtPatientAge)
            txt.text = patient?.age.toString()
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_item)
        }
        //
//
        fun getImageView () : ImageView {
            return view.findViewById(R.id.imgPatientSelfie)
        }

    }

}