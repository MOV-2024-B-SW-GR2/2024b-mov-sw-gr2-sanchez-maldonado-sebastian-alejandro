package org.example

import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Sebastian";
    //inmutable = "Alejandro";
    val fechaNacimiento: Date = Date();
    val estadoCivilWhen = null;
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        ("S") ->{
            println("Soltero")
        }
        else ->{
            println("Ni idea")
        }
    }
    val esSoltero = false;
    val coqueteo = if(esSoltero) "Si" else "No"
    imprimirNombre("Sebastian")

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaA = Suma(1,1)
    val sumaB = Suma(null, 1)
    val sumaC = Suma(1, null)
    val sumaD = Suma(null, null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    val arregloEstatico: kotlin.Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);

    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    val respuestaForEach: Unit = arregloDinamico.forEach {
        valorActual: Int -> println("Valor actual: ${valorActual}");
    }
    arregloDinamico.forEach { println("Valor actual (it): ${it}") }

    val respuestaMap: List<Double> = arregloDinamico
        .map {
            valorActual: Int -> return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    val respuestaFilter: List<Int> = arregloDinamico.filter { valorActual:Int ->
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    val respuestaAny: Boolean = arregloDinamico.any { valorActual:Int ->
        return@any (valorActual > 5)
    }
    println(respuestaAny)
    val respuestaAll: Boolean = arregloDinamico.all { valorActual:Int ->
        return@all (valorActual > 5)
    }
    println(respuestaAll)

    val respuestaReduce: Int = arregloDinamico.reduce { acumulado:Int, valorActual:Int ->
        return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce)
}

fun imprimirNombre(nombre:String): Unit{
    fun otraFuncionAdentro(){
        println("Otra funcion adentro")
    }
    println("Nombre: $nombre")//o ${nombre}
}

fun calcularSueldo(
    sueldo:Double,
    tasa: Double = 12.00,
    bonoEspecial: Double? = null
):Double {
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parametroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

class Suma(
    unoParametro: Int,
    dosParametro: Int,
): Numeros(
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito"
    init {
        this.numeroUno
        this.numeroDos
        numeroUno
        numeroDos
        this.soyPublicoImplicito
        soyPublicoExplicito
    }
    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        //bloque de codigo secundario
    }
    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos
    )
    constructor(
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )
    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{ return num * num}
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}
