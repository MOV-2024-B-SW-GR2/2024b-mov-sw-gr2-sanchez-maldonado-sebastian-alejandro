package com.example.a04_deber01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : FragmentActivity(), OnMapReadyCallback { // 🔹 Cambiamos a FragmentActivity()

    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        try {
            latitud = intent.getDoubleExtra("LATITUD", 0.0)
            longitud = intent.getDoubleExtra("LONGITUD", 0.0)

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            if (mapFragment == null) {
                throw NullPointerException("El fragmento del mapa es null. Revisa activity_mapa.xml.")
            }
            mapFragment.getMapAsync(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val ubicacion = LatLng(latitud, longitud)
        googleMap.addMarker(MarkerOptions().position(ubicacion).title("Ubicación Guardada"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }
}
