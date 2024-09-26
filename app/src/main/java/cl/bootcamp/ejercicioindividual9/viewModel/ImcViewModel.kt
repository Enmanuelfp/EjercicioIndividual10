package cl.bootcamp.ejercicioindividual9.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cl.bootcamp.ejercicioindividual9.model.Paciente // Importamos Paciente desde el modelo

class ImcViewModel : ViewModel() {
    // Estados para gestionar los campos de entrada del formulario
    private var _nombre = mutableStateOf("")
    private var _peso = mutableStateOf("")
    private var _altura = mutableStateOf("")
    private var _edad = mutableStateOf("")
    private var _result = mutableStateOf("")
    private var _sexo = mutableStateOf("")
    private var _estadoSalud = mutableStateOf("")

    var nombre = _nombre
    var peso: MutableState<String> = _peso
    var altura: MutableState<String> = _altura
    var edad: MutableState<String> = _edad
    var result: MutableState<String> = _result
    var sexo: MutableState<String> = _sexo
    var estadoSalud: MutableState<String> = _estadoSalud

    var hasCalculated = mutableStateOf(false)

    // Función para calcular el IMC
    fun Calculo(peso: String, altura: String, edad: String, sexo: String): Float {
        // Reemplazamos comas por puntos para manejar separadores decimales incorrectos
        val pesoFormatted = peso.replace(",", ".")
        val alturaFormatted = altura.replace(",", ".")

        // Convertimos las entradas a valores numéricos
        val pesoD = pesoFormatted.toDoubleOrNull()
        val alturaD = alturaFormatted.toDoubleOrNull()
        val edadD = edad.toIntOrNull()

        // Verificamos si alguno de los campos es nulo o no válido
        if (peso.isEmpty() || altura.isEmpty() || edad.isEmpty() || sexo.isEmpty() ||
            pesoD == null || alturaD == null || edadD == null) {
            hasCalculated.value = true
            return 0.0f
        }

        // Verificamos si los valores son menores o iguales a 0
        if (pesoD <= 0 || alturaD <= 0 || edadD <= 0) {
            hasCalculated.value = true
            return 0.0f
        }

        // Calculamos el IMC
        val resultimc = (pesoD / (alturaD * alturaD) * 10000).toFloat()

        // Actualizamos los resultados
        _result.value = String.format("%.2f", resultimc)
        _estadoSalud.value = estadoSalud(resultimc)

        return resultimc
    }

    // Lista de pacientes (mutable, pero se expone como inmutable)
    private var _pacientes = mutableListOf<Paciente>()
    var pacientes: List<Paciente> = _pacientes

    // Función para agregar un nuevo paciente
    fun AgregarPaciente(nombre: String) {
        val nuevoPaciente = Paciente(nombre)
        _pacientes.add(nuevoPaciente)
    }

    // Función para actualizar un paciente con datos de IMC
    fun ActualizarPaciente(
        nombre: String,
        edad: String,
        sexo: String,
        imc: String,
        estadoSalud: String
    ) {
        val paciente = _pacientes.find { it.nombre == nombre }
        paciente?.apply {
            this.edad = edad
            this.sexo = sexo
            this.imc = imc
            this.estadoSalud = estadoSalud
            this.imcCalculado = true
        }
    }

    // Función para determinar el estado de salud en base al IMC
    fun estadoSalud(resultimc: Float): String {
        return when {
            resultimc < 18.5 -> "Bajo peso"
            resultimc in 18.5..24.9 -> "Peso normal"
            resultimc in 25.0..29.9 -> "Sobrepeso"
            resultimc in 30.0..34.9 -> "Obesidad I"
            resultimc in 35.0..39.9 -> "Obesidad II"
            else -> "Obesidad III"
        }
    }

    // Función para limpiar los campos del formulario
    fun LimpiarCampos() {
        peso.value = ""
        altura.value = ""
        edad.value = ""
        sexo.value = ""
        result.value = ""
        estadoSalud.value = ""
    }
}

