package cl.bootcamp.ejercicioindividual9.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImcViewModel : ViewModel() {
    //Aqui comienza la actividad 10
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

    fun Calculo(peso: String, altura: String, edad: String, sexo: String): Float {
        val pesoD = peso.toDoubleOrNull() ?: 0.0
        val alturaD = altura.toDoubleOrNull() ?: 0.0
        val edadD = edad.toIntOrNull() ?: 0
        if (peso.isEmpty() || altura.isEmpty() || edad.isEmpty() || sexo.isEmpty()) {
            hasCalculated.value = true
            return 0.0f
        }

        if (pesoD <= 0 || alturaD <= 0 || edadD <= 0) {
            hasCalculated.value = true
            return 0.0f
        }

        val resultimc = (pesoD / (alturaD * alturaD) * 10000).toFloat()
        _result.value = String.format("%.2f", resultimc)
        _estadoSalud.value = estadoSalud(resultimc)
        return _result.value.toFloat()

    }


    //Aqui comienza la Tarea 13
    private var _pacientes = mutableListOf<Paciente>()
    var pacientes: List<Paciente> = _pacientes

    fun AgregarPaciente(
        nombre: String
    ) {
        val nuevoPaciente = Paciente(nombre)
        _pacientes.add(nuevoPaciente)
    }

    data class Paciente(
        val nombre: String,
        var edad: String = "",
        var sexo: String = "",
        var imc: String = "",
        var estadoSalud: String = "",
        var imcCalculado: Boolean = false
    )

    //Aqui comienza la tarea 14

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

    fun LimpiarCampos() {
        peso.value = ""
        altura.value = ""
        edad.value = ""
        sexo.value = ""
        result.value = ""
        estadoSalud.value = ""
    }


//Profesor mi ViewModel esta desordenado pero funciona, le dejo un saludo desde estas lineas, fue dificil hacer funcionar
    //la app hasta este punto pero se pudo.

}
