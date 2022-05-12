package com.utn.apputnerds.database

import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import com.utn.apputnerds.entities.Doctor
import com.utn.apputnerds.entities.MedicalRecord
import com.utn.apputnerds.entities.Patient

@Database(entities = [Doctor::class, Patient::class, MedicalRecord::class], version = 3, exportSchema = false)

public  abstract class appDatabase : RoomDatabase() {

    abstract fun doctorDao(): doctorDao
    abstract fun patientDao(): patientDao
    abstract fun medicalrecordDao(): medicalrecordDao

    companion object {
        var INSTANCE: appDatabase? = null

        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDatabase::class.java,
                        "myDB"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}