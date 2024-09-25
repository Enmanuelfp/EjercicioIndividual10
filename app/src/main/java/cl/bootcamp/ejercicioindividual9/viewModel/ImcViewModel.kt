package cl.bootcamp.ejercicioindividual9.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImcViewModel: ViewModel(){
//Aqui comienza la actividad 10
    private var _peso = mutableStateOf("")
    private var _altura = mutableStateOf("")
    private var _edad =  mutableStateOf("")
    private var _result = mutableStateOf("")
    private var _sexo = mutableStateOf("")

    var peso : MutableState<String> = _peso
    var altura : MutableState<String> = _altura
    var edad : MutableState<String> = _edad
    var result : MutableState<String> = _result
    var sexo : MutableState<String> = _sexo
    var hasCalculated = mutableStateOf(false)

    fun Calculo(peso: String, altura: String, edad: String, sexo:String): Float {
        val pesoD = peso.toDoubleOrNull() ?: 0.0
        val alturaD = altura.toDoubleOrNull() ?: 0.0
        val edadD = edad.toIntOrNull() ?: 0
        if (peso.isEmpty() || altura.isEmpty()||edad.isEmpty() || sexo.isEmpty()) {
            hasCalculated.value = true
            _result.value = "Valores inválidos"
            return 0.0f
        }

        if (pesoD <= 0 || alturaD <= 0 || edadD <= 0) {
            hasCalculated.value = true
            _result.value = "Valores numéricos inválidos"
            return 0.0f
        }

        val resultimc = (pesoD / (alturaD * alturaD) * 10000).toFloat()
        _result.value = String.format("%.2f", resultimc)
        return _result.value.toFloat()
    }


    //Aqui comienza la Tarea 13
    private var _pacientes = mutableListOf<Paciente>()
    var pacientes : List<Paciente> = _pacientes

    fun agregarPaciente(nombre : String){
        val nuevoPaciente = Paciente(nombre)
        _pacientes.add(nuevoPaciente)
    }

    data class Paciente(val nombre:String)







}
