package com.example.a04_deber01.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "AppDB", null, 3) { // Subimos a version 3
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Celular(id INTEGER PRIMARY KEY AUTOINCREMENT, marca TEXT, modelo TEXT, precio REAL, latitud REAL, longitud REAL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS Aplicativo(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, version TEXT, celular_id INTEGER, FOREIGN KEY(celular_id) REFERENCES Celular(id) ON DELETE CASCADE)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) { // Agregar columnas latitud y longitud sin borrar datos
            db.execSQL("ALTER TABLE Celular ADD COLUMN latitud REAL DEFAULT NULL")
            db.execSQL("ALTER TABLE Celular ADD COLUMN longitud REAL DEFAULT NULL")
        }
    }
}
