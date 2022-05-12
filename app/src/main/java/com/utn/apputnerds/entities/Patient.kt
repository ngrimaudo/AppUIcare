package com.utn.apputnerds.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
class Patient (name: String?, lastName: String?, age: Int?, urlSelfie: String?, doctor: String?){

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "lastname")
    var lastName: String = ""

    @ColumnInfo(name = "age")
    var age: Int

    @ColumnInfo(name = "urlSelfie")
    var urlSelfie: String = ""

    @ColumnInfo(name = "doctor")
    var doctor: String = ""


    init {
        this.name = name!!
        this.lastName = lastName!!
        this.age = age!!
        this.urlSelfie = urlSelfie!!
        this.doctor = doctor!!
    }
}