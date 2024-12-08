import java.io.*
import java.time.LocalDate

class CelularRepositorio {
    private val archivoCelulares = File("celulares.dat")

    fun crear(celular: Celular) {
        val celulares = leerTodos().toMutableList()

        // Verificar si ya existe un celular con la misma marca
        if (celulares.any { it.marca == celular.marca }) {
            throw IllegalArgumentException("Ya existe un celular con esta marca")
        }

        celulares.add(celular)
        guardarTodos(celulares)
    }

    fun leerTodos(): List<Celular> {
        if (!archivoCelulares.exists()) return emptyList()

        return try {
            ObjectInputStream(FileInputStream(archivoCelulares)).use { input ->
                input.readObject() as? List<Celular> ?: emptyList()
            }
        } catch (e: Exception) {
            // Manejar posibles errores de lectura
            println("Error al leer archivo de celulares: ${e.message}")
            emptyList()
        }
    }

    fun buscarPorMarca(marca: String): Celular? {
        return leerTodos().find { it.marca == marca }
    }

    fun actualizar(marca: String, celularActualizado: Celular) {
        val celulares = leerTodos().toMutableList()
        val indice = celulares.indexOfFirst { it.marca == marca }

        if (indice != -1) {
            // Mantener la lista de aplicativos original
            val aplicativosOriginales = celulares[indice].aplicativos
            val celularFinal = celularActualizado.copy(aplicativos = aplicativosOriginales)
            celulares[indice] = celularFinal
            guardarTodos(celulares)
        } else {
            throw IllegalArgumentException("No se encontró un celular con la marca $marca")
        }
    }

    fun eliminar(marca: String) {
        val celulares = leerTodos().toMutableList()
        val eliminado = celulares.removeIf { it.marca == marca }

        if (eliminado) {
            guardarTodos(celulares)
        } else {
            throw IllegalArgumentException("No se encontró un celular con la marca $marca")
        }
    }

    private fun guardarTodos(celulares: List<Celular>) {
        try {
            ObjectOutputStream(FileOutputStream(archivoCelulares)).use { output ->
                output.writeObject(celulares)
            }
        } catch (e: Exception) {
            println("Error al guardar archivo de celulares: ${e.message}")
        }
    }

    // Método para agregar un aplicativo a un celular específico
    fun agregarAplicativo(marcaCelular: String, aplicativo: Aplicativo) {
        val celulares = leerTodos().toMutableList()
        val indiceCelular = celulares.indexOfFirst { it.marca == marcaCelular }

        if (indiceCelular != -1) {
            val celularExistente = celulares[indiceCelular]

            // Verificar si el aplicativo ya existe
            if (celularExistente.aplicativos.none { it.nombre == aplicativo.nombre }) {
                celularExistente.aplicativos.add(aplicativo)
                guardarTodos(celulares)
            } else {
                throw IllegalArgumentException("El aplicativo ya existe en este celular")
            }
        } else {
            throw IllegalArgumentException("No se encontró un celular con la marca $marcaCelular")
        }
    }

    // Método para eliminar un aplicativo de un celular
    fun eliminarAplicativo(marcaCelular: String, nombreAplicativo: String) {
        val celulares = leerTodos().toMutableList()
        val indiceCelular = celulares.indexOfFirst { it.marca == marcaCelular }

        if (indiceCelular != -1) {
            val celularExistente = celulares[indiceCelular]
            val aplicativoEliminado = celularExistente.aplicativos.removeIf { it.nombre == nombreAplicativo }

            if (aplicativoEliminado) {
                guardarTodos(celulares)
            } else {
                throw IllegalArgumentException("No se encontró el aplicativo $nombreAplicativo en el celular")
            }
        } else {
            throw IllegalArgumentException("No se encontró un celular con la marca $marcaCelular")
        }
    }
}

class AplicativoRepositorio {
    private val archivoAplicativos = File("aplicativos.dat")

    fun crear(aplicativo: Aplicativo) {
        val aplicativos = leerTodos().toMutableList()

        // Verificar si ya existe un aplicativo con el mismo nombre
        if (aplicativos.any { it.nombre == aplicativo.nombre }) {
            throw IllegalArgumentException("Ya existe un aplicativo con este nombre")
        }

        aplicativos.add(aplicativo)
        guardarTodos(aplicativos)
    }

    fun leerTodos(): List<Aplicativo> {
        if (!archivoAplicativos.exists()) return emptyList()

        return try {
            ObjectInputStream(FileInputStream(archivoAplicativos)).use { input ->
                input.readObject() as? List<Aplicativo> ?: emptyList()
            }
        } catch (e: Exception) {
            // Manejar posibles errores de lectura
            println("Error al leer archivo de aplicativos: ${e.message}")
            emptyList()
        }
    }

    fun buscarPorNombre(nombre: String): Aplicativo? {
        return leerTodos().find { it.nombre == nombre }
    }

    fun actualizar(nombre: String, aplicativoActualizado: Aplicativo) {
        val aplicativos = leerTodos().toMutableList()
        val indice = aplicativos.indexOfFirst { it.nombre == nombre }

        if (indice != -1) {
            aplicativos[indice] = aplicativoActualizado
            guardarTodos(aplicativos)
        } else {
            throw IllegalArgumentException("No se encontró un aplicativo con el nombre $nombre")
        }
    }

    fun eliminar(nombre: String) {
        val aplicativos = leerTodos().toMutableList()
        val eliminado = aplicativos.removeIf { it.nombre == nombre }

        if (eliminado) {
            guardarTodos(aplicativos)
        } else {
            throw IllegalArgumentException("No se encontró un aplicativo con el nombre $nombre")
        }
    }

    private fun guardarTodos(aplicativos: List<Aplicativo>) {
        try {
            ObjectOutputStream(FileOutputStream(archivoAplicativos)).use { output ->
                output.writeObject(aplicativos)
            }
        } catch (e: Exception) {
            println("Error al guardar archivo de aplicativos: ${e.message}")
        }
    }

    // Método para obtener aplicativos de un celular específico
    fun obtenerAplicativosPorMarca(marcaCelular: String): List<Aplicativo> {
        return leerTodos().filter { it.marcaCelular == marcaCelular }
    }
}