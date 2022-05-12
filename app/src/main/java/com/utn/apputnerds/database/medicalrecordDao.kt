package com.utn.apputnerds.database

import androidx.room.*
import com.utn.apputnerds.entities.MedicalRecord

@Dao
public interface medicalrecordDao {

    @Query("SELECT * FROM MedicalRecord ORDER BY id")
    fun selectAll(): MutableList<MedicalRecord>

    @Query("SELECT * FROM MedicalRecord WHERE patient = :patient")
    fun selectMedicalRecordByPatient(patient: String?): MutableList<MedicalRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicalRecord(MedicalRecord: MedicalRecord?)

    @Update
    fun updateMedicalRecord(MedicalRecord: MedicalRecord?)

    @Delete
    fun delete(MedicalRecord: MedicalRecord?)

    @Query("SELECT * FROM MedicalRecord WHERE id = :id")
    fun loadMedicalRecordById(id: Int): MedicalRecord?

    @Query("SELECT * FROM MedicalRecord WHERE patient = :patient")
    fun loadMedicalRecordByPatient(patient: String?): MedicalRecord?
}