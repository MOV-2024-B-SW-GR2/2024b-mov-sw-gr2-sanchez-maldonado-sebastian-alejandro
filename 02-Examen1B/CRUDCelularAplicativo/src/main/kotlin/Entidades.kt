import java.time.LocalDate
import java.io.Serializable

data class Celular(
    val marca: String,
    val modelo: String,
    val fechaLanzamiento: LocalDate,
    val esTactil: Boolean,
    val precio: Double,
    val aplicativos: MutableList<Aplicativo> = mutableListOf()
) : Serializable {
    // Método para agregar un aplicativo
    fun agregarAplicativo(aplicativo: Aplicativo) {
        aplicativos.add(aplicativo)
    }
}

data class Aplicativo(
    val nombre: String,
    val version: String,
    val fechaCreacion: LocalDate,
    val esGratuito: Boolean,
    val tamanoEnMB: Int,
    val marcaCelular: String? = null  // Guardamos la marca del celular en lugar de la referencia completa
) : Serializable