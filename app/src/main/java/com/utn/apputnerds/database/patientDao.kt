package com.utn.apputnerds.database

import androidx.room.*
import com.utn.apputnerds.entities.Patient

@Dao
public interface patientDao {

    @Query("SELECT * FROM Patient ORDER BY id")
    fun selectAll(): MutableList<Patient>

    @Query("SELECT name FROM patient WHERE name = :name and doctor = :doctor")
    fun validate(name: String?, doctor: String?): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatient(Patient: Patient?)

    @Update
    fun updatePatient(Patient: Patient?)

    @Delete
    fun delete(Patient: Patient?)

    @Query("SELECT * FROM Patient WHERE id = :id")
    fun loadPatientById(id: Int): Patient?

    @Query("SELECT * FROM Patient WHERE doctor = :doctor")
    fun getPatients(doctor: String?): MutableList<Patient>

    @Query("SELECT * FROM Patient WHERE name = :name")
    fun loadPatientByName(name: String?): Patient?

    @Query("SELECT COUNT(*) FROM Patient WHERE name = :name and lastname = :lastname")
    fun isPatientAvailable(name: String?, lastname: String?): Int

    @Query("SELECT COUNT(*) FROM Patient WHERE doctor = :doctor")
    fun qtyPatientByDoctor(doctor: String?): Int

}