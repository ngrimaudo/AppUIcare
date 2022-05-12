package com.utn.apputnerds.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//adbreitman@gmail.com  mail del profe

@Entity(tableName = "doctor")
class Doctor (name : String, password : String, mail : String, speciality : String) {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "password")
    var password : String

    @ColumnInfo(name = "mail")
    var mail : String

    @ColumnInfo(name = "speciality")
    var speciality : String



    init {
        this.name = name
        this.password = password
        this.mail = mail
        this.speciality = speciality

    }
}