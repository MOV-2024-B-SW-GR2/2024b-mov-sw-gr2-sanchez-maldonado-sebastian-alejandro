package com.example.a04_deber01

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.a04_deber01.database.DatabaseHelper

class CelularActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editMarca: EditText
    private lateinit var editModelo: EditText
    private lateinit var editPrecio: EditText
    private lateinit var editLatitud: EditText
    private lateinit var editLongitud: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnVerMapa: Button
    private lateinit var btnVerAplicativos: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val celularesList = mutableListOf<String>()
    private var selectedId: Int? = null  // Para saber qué registro se está editando/eliminando

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celular)

        // Inicializar vistas
        dbHelper = DatabaseHelper(this)
        editMarca = findViewById(R.id.editMarca)
        editModelo = findViewById(R.id.editModelo)
        editPrecio = findViewById(R.id.editPrecio)
        editLatitud = findViewById(R.id.editLatitud)
        editLongitud = findViewById(R.id.editLongitud)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnVerMapa = findViewById(R.id.btnVerMapa)
        btnVerAplicativos = findViewById(R.id.btnVerAplicativos)
        listView = findViewById(R.id.listViewCelulares)

        // Cargar celulares desde la base de datos
        cargarCelulares()

        // Acción del botón Guardar / Actualizar
        btnGuardar.setOnClickListener {
            if (selectedId == null) {
                agregarCelular()
            } else {
                actualizarCelular()
            }
        }

        // Acción del botón Eliminar
        btnEliminar.setOnClickListener {
            eliminarCelular()
        }

        // Botón para abrir el mapa con la ubicación guardada
        btnVerMapa.setOnClickListener {
            val latitud = editLatitud.text.toString().toDoubleOrNull()
            val longitud = editLongitud.text.toString().toDoubleOrNull()

            if (latitud != null && longitud != null) {
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra("LATITUD", latitud)
                intent.putExtra("LONGITUD", longitud)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay coordenadas guardadas", Toast.LENGTH_SHORT).show()
            }
        }

        // Permitir selección para editar/eliminar/ver aplicativos
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = celularesList[position]
            val parts = selectedItem.split(" - ") // Formato: "ID - Marca Modelo - $Precio"
            selectedId = parts[0].toInt()
            editMarca.setText(parts[1].split(" ")[0])
            editModelo.setText(parts[1].split(" ")[1])
            editPrecio.setText(parts[2].replace("$", ""))

            // Obtener la latitud y longitud desde la base de datos
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT latitud, longitud FROM Celular WHERE id=?", arrayOf(selectedId.toString()))
            if (cursor.moveToFirst()) {
                editLatitud.setText(cursor.getString(0) ?: "")
                editLongitud.setText(cursor.getString(1) ?: "")
            }
            cursor.close()
            db.close()

            btnGuardar.text = "Actualizar"
            btnEliminar.visibility = Button.VISIBLE
            btnVerAplicativos.visibility = Button.VISIBLE
            btnVerMapa.visibility = Button.VISIBLE
        }

        // Abrir la lista de aplicativos del celular seleccionado
        btnVerAplicativos.setOnClickListener {
            selectedId?.let { celularId ->
                val intent = Intent(this, AplicativoActivity::class.java)
                intent.putExtra("CELULAR_ID", celularId)
                startActivity(intent)
            }
        }
    }

    private fun agregarCelular() {
        val marca = editMarca.text.toString()
        val modelo = editModelo.text.toString()
        val precio = editPrecio.text.toString()
        val latitud = editLatitud.text.toString().toDoubleOrNull()
        val longitud = editLongitud.text.toString().toDoubleOrNull()

        if (marca.isNotEmpty() && modelo.isNotEmpty() && precio.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("marca", marca)
                put("modelo", modelo)
                put("precio", precio.toDouble())
                put("latitud", latitud)
                put("longitud", longitud)
            }
            db.insert("Celular", null, values)
            db.close()
            limpiarCampos()
            cargarCelulares()
        } else {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarCelular() {
        val marca = editMarca.text.toString()
        val modelo = editModelo.text.toString()
        val precio = editPrecio.text.toString()
        val latitud = editLatitud.text.toString().toDoubleOrNull()
        val longitud = editLongitud.text.toString().toDoubleOrNull()

        if (selectedId != null && marca.isNotEmpty() && modelo.isNotEmpty() && precio.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("marca", marca)
                put("modelo", modelo)
                put("precio", precio.toDouble())
                put("latitud", latitud)
                put("longitud", longitud)
            }
            db.update("Celular", values, "id=?", arrayOf(selectedId.toString()))
            db.close()

            Toast.makeText(this, "Celular actualizado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
            cargarCelulares()
        }
    }

    private fun eliminarCelular() {
        if (selectedId != null) {
            val db = dbHelper.writableDatabase
            db.delete("Celular", "id=?", arrayOf(selectedId.toString()))
            db.close()

            Toast.makeText(this, "Celular eliminado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
            cargarCelulares()
        }
    }

    private fun cargarCelulares() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Celular", null)
        celularesList.clear()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val marca = cursor.getString(1)
            val modelo = cursor.getString(2)
            val precio = cursor.getDouble(3)
            celularesList.add("$id - $marca $modelo - $$precio")
        }

        cursor.close()
        db.close()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, celularesList)
        listView.adapter = adapter
    }

    private fun limpiarCampos() {
        editMarca.text.clear()
        editModelo.text.clear()
        editPrecio.text.clear()
        editLatitud.text.clear()
        editLongitud.text.clear()
        btnGuardar.text = "Guardar"
        btnEliminar.visibility = Button.GONE
        btnVerMapa.visibility = Button.GONE
        btnVerAplicativos.visibility = Button.GONE
        selectedId = null
    }
}
