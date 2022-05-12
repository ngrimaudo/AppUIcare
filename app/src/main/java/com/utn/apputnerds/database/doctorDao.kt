package com.utn.apputnerds.database

import androidx.room.*
import com.utn.apputnerds.entities.Doctor

@Dao
public interface doctorDao {

    @Query("SELECT * FROM Doctor ORDER BY id")
    fun selectAll(): MutableList<Doctor>

    @Query("SELECT name FROM Doctor WHERE name = :name and password = :password")
    fun validate(name: String?, password: String?): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctor(doctor: Doctor?)

    @Update
    fun updateDoctor(doctor: Doctor?)

    @Delete
    fun delete(doctor: Doctor?)

    @Query("SELECT * FROM Doctor WHERE id = :id")
    fun loadDoctorById(id: Int): Doctor?

    @Query("SELECT * FROM Doctor WHERE name = :name")
    fun loadDoctorByName(name: String?): Doctor?

    @Query("SELECT COUNT(*) FROM Doctor WHERE name = :name and mail = :mail")
    fun isDoctorAvailable(name: String?, mail: String?): Int

}