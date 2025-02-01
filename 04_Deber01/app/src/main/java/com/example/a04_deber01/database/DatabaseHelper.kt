package com.example.a04_deber01.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "AppDB", null, 2) { // Cambiamos a version 2
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Celular(id INTEGER PRIMARY KEY AUTOINCREMENT, marca TEXT, modelo TEXT, precio REAL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS Aplicativo(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, version TEXT, celular_id INTEGER, FOREIGN KEY(celular_id) REFERENCES Celular(id) ON DELETE CASCADE)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) { // Si la versión anterior es menor a 2, creamos la tabla de Aplicativos sin borrar datos
            db.execSQL("CREATE TABLE IF NOT EXISTS Aplicativo(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, version TEXT, celular_id INTEGER, FOREIGN KEY(celular_id) REFERENCES Celular(id) ON DELETE CASCADE)")
        }
    }
}
