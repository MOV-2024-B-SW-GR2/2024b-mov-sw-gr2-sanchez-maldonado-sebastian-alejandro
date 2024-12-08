import java.time.LocalDate

fun main() {
    val celularRepositorio = CelularRepositorio()
    val aplicativoRepositorio = AplicativoRepositorio()

    while (true) {
        println("\n--- MENÚ PRINCIPAL ---")
        println("1. Gestionar Celulares")
        println("2. Gestionar Aplicativos")
        println("3. Salir")
        print("Seleccione una opción: ")

        when (readln()) {
            "1" -> menuCelulares(celularRepositorio, aplicativoRepositorio)
            "2" -> menuAplicativos(aplicativoRepositorio, celularRepositorio)
            "3" -> break
            else -> println("Opción inválida")
        }
    }
}

fun menuCelulares(
    celularRepositorio: CelularRepositorio,
    aplicativoRepositorio: AplicativoRepositorio
) {
    while (true) {
        println("\n--- GESTIÓN DE CELULARES ---")
        println("1. Crear Celular")
        println("2. Listar Celulares")
        println("3. Listar Aplicativos de un Celular")
        println("4. Actualizar Celular")
        println("5. Eliminar Celular")
        println("6. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (readln()) {
            "1" -> {
                println("Ingrese los datos del celular:")
                val nuevoCelular = pedirDatosCelular()
                celularRepositorio.crear(nuevoCelular)
                println("Celular creado exitosamente")
            }
            "2" -> {
                val celulares = celularRepositorio.leerTodos()
                if (celulares.isEmpty()) {
                    println("No hay celulares registrados")
                } else {
                    celulares.forEach { celular ->
                        println(celular)
                        // Mostrar aplicativos del celular
                        val aplicativos = aplicativoRepositorio.obtenerAplicativosPorMarca(celular.marca)
                        if (aplicativos.isNotEmpty()) {
                            println("  Aplicativos:")
                            aplicativos.forEach { println("    - $it") }
                        }
                    }
                }
            }
            "3" -> {
                print("Ingrese la marca del celular: ")
                val marca = readln()
                val aplicativos = aplicativoRepositorio.obtenerAplicativosPorMarca(marca)
                if (aplicativos.isNotEmpty()) {
                    println("Aplicativos del celular $marca:")
                    aplicativos.forEach { println(it) }
                } else {
                    println("No hay aplicativos para este celular")
                }
            }
            "4" -> {
                print("Ingrese la marca del celular a actualizar: ")
                val marca = readln()
                println("Ingrese los nuevos datos del celular:")
                val celularActualizado = pedirDatosCelular()
                celularRepositorio.actualizar(marca, celularActualizado)
                println("Celular actualizado exitosamente")
            }
            "5" -> {
                print("Ingrese la marca del celular a eliminar: ")
                val marca = readln()

                // Eliminar primero los aplicativos asociados
                val aplicativosAsociados = aplicativoRepositorio.obtenerAplicativosPorMarca(marca)
                aplicativosAsociados.forEach { aplicativo ->
                    aplicativoRepositorio.eliminar(aplicativo.nombre)
                }

                // Luego eliminar el celular
                celularRepositorio.eliminar(marca)
                println("Celular y sus aplicativos eliminados exitosamente")
            }
            "6" -> break
            else -> println("Opción inválida")
        }
    }
}

fun menuAplicativos(
    aplicativoRepositorio: AplicativoRepositorio,
    celularRepositorio: CelularRepositorio
) {
    while (true) {
        println("\n--- GESTIÓN DE APLICATIVOS ---")
        println("1. Crear Aplicativo")
        println("2. Listar Aplicativos")
        println("3. Listar Aplicativos por Celular")
        println("4. Actualizar Aplicativo")
        println("5. Eliminar Aplicativo")
        println("6. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (readln()) {
            "1" -> {
                println("Ingrese los datos del aplicativo:")
                val nuevoAplicativo = pedirDatosAplicativo()
                aplicativoRepositorio.crear(nuevoAplicativo)

                // Si se asoció a un celular, agregarlo
                nuevoAplicativo.marcaCelular?.let { marca ->
                    // Verificar si el celular existe
                    val celulares = celularRepositorio.leerTodos()
                    if (celulares.any { it.marca == marca }) {
                        println("Aplicativo creado y asociado al celular $marca")
                    } else {
                        println("Advertencia: La marca del celular no existe")
                    }
                }

                println("Aplicativo creado exitosamente")
            }
            "2" -> {
                val aplicativos = aplicativoRepositorio.leerTodos()
                if (aplicativos.isEmpty()) {
                    println("No hay aplicativos registrados")
                } else {
                    aplicativos.forEach { println(it) }
                }
            }
            "3" -> {
                print("Ingrese la marca del celular: ")
                val marca = readln()
                val aplicativos = aplicativoRepositorio.obtenerAplicativosPorMarca(marca)
                if (aplicativos.isNotEmpty()) {
                    println("Aplicativos para el celular $marca:")
                    aplicativos.forEach { println(it) }
                } else {
                    println("No hay aplicativos para este celular")
                }
            }
            "4" -> {
                print("Ingrese el nombre del aplicativo a actualizar: ")
                val nombre = readln()

                // Verificar si el aplicativo existe
                val aplicativosExistentes = aplicativoRepositorio.leerTodos()
                val aplicativoExistente = aplicativosExistentes.find { it.nombre == nombre }

                if (aplicativoExistente != null) {
                    println("Ingrese los nuevos datos del aplicativo:")
                    val aplicativoActualizado = pedirDatosAplicativo()
                    aplicativoRepositorio.actualizar(nombre, aplicativoActualizado)
                    println("Aplicativo actualizado exitosamente")
                } else {
                    println("El aplicativo no existe")
                }
            }
            "5" -> {
                print("Ingrese el nombre del aplicativo a eliminar: ")
                val nombre = readln()

                // Verificar si el aplicativo existe
                val aplicativosExistentes = aplicativoRepositorio.leerTodos()
                val aplicativoExistente = aplicativosExistentes.find { it.nombre == nombre }

                if (aplicativoExistente != null) {
                    aplicativoRepositorio.eliminar(nombre)
                    println("Aplicativo eliminado exitosamente")
                } else {
                    println("El aplicativo no existe")
                }
            }
            "6" -> break
            else -> println("Opción inválida")
        }
    }
}

fun pedirDatosCelular(): Celular {
    print("Marca: ")
    val marca = readln()
    print("Modelo: ")
    val modelo = readln()
    print("Fecha de lanzamiento (YYYY-MM-DD): ")
    val fechaLanzamiento = LocalDate.parse(readln())
    print("¿Es táctil? (true/false): ")
    val esTactil = readln().toBoolean()
    print("Precio: ")
    val precio = readln().toDouble()

    return Celular(marca, modelo, fechaLanzamiento, esTactil, precio)
}

fun pedirDatosAplicativo(): Aplicativo {
    print("Nombre: ")
    val nombre = readln()
    print("Versión: ")
    val version = readln()
    print("Fecha de creación (YYYY-MM-DD): ")
    val fechaCreacion = LocalDate.parse(readln())
    print("¿Es gratuito? (true/false): ")
    val esGratuito = readln().toBoolean()
    print("Tamaño en MB: ")
    val tamanoEnMB = readln().toInt()
    print("Marca del celular (dejar en blanco si no aplica): ")
    val marcaCelular = readln().takeIf { it.isNotBlank() }

    return Aplicativo(nombre, version, fechaCreacion, esGratuito, tamanoEnMB, marcaCelular)
}