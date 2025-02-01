package com.example.a04_deber01

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
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val celularesList = mutableListOf<String>()
    private var selectedId: Int? = null  // Para saber qué registro se está editando/eliminando

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celular)

        // Inicializar vistas
        dbHelper = DatabaseHelper(this)
        editMarca = findViewById(R.id.editMarca)
        editModelo = findViewById(R.id.editModelo)
        editPrecio = findViewById(R.id.editPrecio)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
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

        // Permitir selección para editar/eliminar
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = celularesList[position]
            val parts = selectedItem.split(" - ") // Formato: "ID - Marca Modelo - $Precio"
            selectedId = parts[0].toInt()
            editMarca.setText(parts[1].split(" ")[0])
            editModelo.setText(parts[1].split(" ")[1])
            editPrecio.setText(parts[2].replace("$", ""))

            btnGuardar.text = "Actualizar"  // Cambiar el texto del botón
            btnEliminar.visibility = Button.VISIBLE  // Mostrar el botón Eliminar
        }

        val btnVerAplicativos = findViewById<Button>(R.id.btnVerAplicativos)

        // Permitir abrir la lista de aplicativos del celular seleccionado
        btnVerAplicativos.setOnClickListener {
            selectedId?.let { celularId ->
                val intent = Intent(this, AplicativoActivity::class.java)
                intent.putExtra("CELULAR_ID", celularId)
                startActivity(intent)
            }
        }

        // Mostrar el botón "Ver Aplicativos" cuando se selecciona un celular
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = celularesList[position]
            val parts = selectedItem.split(" - ")
            selectedId = parts[0].toInt()
            editMarca.setText(parts[1].split(" ")[0])
            editModelo.setText(parts[1].split(" ")[1])
            editPrecio.setText(parts[2].replace("$", ""))

            btnGuardar.text = "Actualizar"
            btnEliminar.visibility = Button.VISIBLE
            btnVerAplicativos.visibility = Button.VISIBLE // Mostrar el botón
        }

    }

    private fun agregarCelular() {
        val marca = editMarca.text.toString()
        val modelo = editModelo.text.toString()
        val precio = editPrecio.text.toString()

        if (marca.isNotEmpty() && modelo.isNotEmpty() && precio.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("marca", marca)
                put("modelo", modelo)
                put("precio", precio.toDouble())
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

        if (selectedId != null && marca.isNotEmpty() && modelo.isNotEmpty() && precio.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("marca", marca)
                put("modelo", modelo)
                put("precio", precio.toDouble())
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
        btnGuardar.text = "Guardar" // Restaurar el botón a su estado original
        btnEliminar.visibility = Button.GONE // Ocultar el botón Eliminar
        selectedId = null
    }
}
