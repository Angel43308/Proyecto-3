package com.example.practica_01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperAlumno(contex:Context): SQLiteOpenHelper(contex,DB_name,null,DB_version){
    companion object{
        private val DB_version= 1
        private val DB_name= "dbalumnos"
        private val nomTable= "alumnos"
        private val keyId ="id"
        private val nom = "nombre"
        private val cue = "cuenta"
        private val corr = "correo"
        private val img= "imagen"
    }
    val sqlCreate: String= "CREATE TABLE $nomTable ($keyId INTEGER PRIMARY KEY, $nom TEXT, $cue TEXT, $corr TEXT, $img TEXT)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $nomTable ")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
}