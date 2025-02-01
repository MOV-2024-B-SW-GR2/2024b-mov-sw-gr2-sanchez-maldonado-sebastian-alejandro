package com.example.a04_deber01

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a04_deber01.database.DatabaseHelper

class AplicativoActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editNombre: EditText
    private lateinit var editVersion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val aplicativosList = mutableListOf<String>()
    private var selectedId: Int? = null
    private var celularId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aplicativo)

        dbHelper = DatabaseHelper(this)
        editNombre = findViewById(R.id.editNombre)
        editVersion = findViewById(R.id.editVersion)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        listView = findViewById(R.id.listViewAplicativos)

        // Obtener el ID del celular desde el Intent
        celularId = intent.getIntExtra("CELULAR_ID", -1)
        if (celularId == -1) {
            Toast.makeText(this, "Error al cargar celular", Toast.LENGTH_SHORT).show()
            finish()
        }

        cargarAplicativos()

        btnGuardar.setOnClickListener {
            if (selectedId == null) {
                agregarAplicativo()
            } else {
                actualizarAplicativo()
            }
        }

        btnEliminar.setOnClickListener {
            eliminarAplicativo()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = aplicativosList[position]
            val parts = selectedItem.split(" - ")
            selectedId = parts[0].toInt()
            editNombre.setText(parts[1])
            editVersion.setText(parts[2])
            btnGuardar.text = "Actualizar"
            btnEliminar.visibility = Button.VISIBLE
        }
    }

    private fun agregarAplicativo() {
        val nombre = editNombre.text.toString()
        val version = editVersion.text.toString()

        if (nombre.isNotEmpty() && version.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("version", version)
                put("celular_id", celularId)
            }
            db.insert("Aplicativo", null, values)
            db.close()
            limpiarCampos()
            cargarAplicativos()
        } else {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarAplicativo() {
        val nombre = editNombre.text.toString()
        val version = editVersion.text.toString()

        if (selectedId != null && nombre.isNotEmpty() && version.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("version", version)
            }
            db.update("Aplicativo", values, "id=?", arrayOf(selectedId.toString()))
            db.close()

            Toast.makeText(this, "Aplicativo actualizado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
            cargarAplicativos()
        }
    }

    private fun eliminarAplicativo() {
        if (selectedId != null) {
            val db = dbHelper.writableDatabase
            db.delete("Aplicativo", "id=?", arrayOf(selectedId.toString()))
            db.close()

            Toast.makeText(this, "Aplicativo eliminado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
            cargarAplicativos()
        }
    }

    private fun cargarAplicativos() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Aplicativo WHERE celular_id=?", arrayOf(celularId.toString()))
        aplicativosList.clear()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val version = cursor.getString(2)
            aplicativosList.add("$id - $nombre - $version")
        }

        cursor.close()
        db.close()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, aplicativosList)
        listView.adapter = adapter
    }

    private fun limpiarCampos() {
        editNombre.text.clear()
        editVersion.text.clear()
        btnGuardar.text = "Guardar"
        btnEliminar.visibility = Button.GONE
        selectedId = null
    }
}