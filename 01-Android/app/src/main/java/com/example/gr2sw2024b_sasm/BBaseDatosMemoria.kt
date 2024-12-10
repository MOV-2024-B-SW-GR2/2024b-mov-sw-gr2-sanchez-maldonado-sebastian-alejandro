package com.example.gr2sw2024b_sasm

class BBaseDatosMemoria {
    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1,"A","a@a.com"))
            arregloBEntrenador.add(BEntrenador(2,"B","b@b.com"))
            arregloBEntrenador.add(BEntrenador(3,"C","c@c.com"))
        }
    }
}