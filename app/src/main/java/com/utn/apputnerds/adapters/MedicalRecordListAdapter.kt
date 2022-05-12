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
import com.utn.apputnerds.entities.MedicalRecord

class MedicalRecordListAdapter (
    private var medicalrecordList: MutableList<MedicalRecord>?,
    val context: Context,
    val onItemClick : (Int) -> Unit


) : RecyclerView.Adapter<MedicalRecordListAdapter.MedicalRecordHolder>(){

    companion object {

        private val TAG = "MedicalRecordListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MedicalRecordListAdapter.MedicalRecordHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_medicalrecord,parent,false)
        return (MedicalRecordHolder(view))

    }

    override fun onBindViewHolder(holder: MedicalRecordListAdapter.MedicalRecordHolder, position: Int) {
        holder.setDate(medicalrecordList?.get(position))
        holder.setDiagnosis(medicalrecordList?.get(position))
        holder.setTreatment(medicalrecordList?.get(position))
        holder.getCardLayout().setOnClickListener  () {
            onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return medicalrecordList!!.size
    }

    class MedicalRecordHolder (v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setDate(medicalrecord: MedicalRecord?) {
            val txt: TextView = view.findViewById(R.id.txtDate)
            txt.text = medicalrecord?.date
        }

        fun setDiagnosis(medicalrecord: MedicalRecord?) {
            val txt: TextView = view.findViewById(R.id.txtDiagnosis)
            txt.text = medicalrecord?.diagnosis
        }

        fun setTreatment(medicalrecord: MedicalRecord?) {
            val txt: TextView = view.findViewById(R.id.txtTreatment)
            txt.text = medicalrecord?.treatment
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_item)
        }

    }

}