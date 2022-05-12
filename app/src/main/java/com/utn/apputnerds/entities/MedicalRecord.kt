package com.utn.apputnerds.entities


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "medicalrecord")
class MedicalRecord(date: String, patient: String?, diagnosis: String?, treatment: String?){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0

    @ColumnInfo(name = "date")
    var date : String = ""

    @ColumnInfo(name = "patient")
    var patient : String = ""

    @ColumnInfo(name = "diagnosis")
    var diagnosis : String = ""

    @ColumnInfo(name = "treatment")
    var treatment : String = ""


    init {
        this.date = date.toString()!!
        this.patient = patient!!
        this.diagnosis = diagnosis!!
        this.treatment = treatment!!
    }
}