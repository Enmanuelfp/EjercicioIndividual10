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

    var peso : MutableState<String> = _peso
    var altura : MutableState<String> = _altura
    var edad : MutableState<String> = _edad
    var result : MutableState<String> = _result
    var hasCalculated = mutableStateOf(false)

    fun Calculo(peso: String, altura: String): Float {
        val pesoD = peso.toDoubleOrNull() ?: 0.0
        val alturaD = altura.toDoubleOrNull() ?: 0.0
        if (peso.isEmpty() || altura.isEmpty()) {
            hasCalculated.value = true
            _result.value = "Valores inválidos"
            return 0.0f
        }

        val resultimc = (pesoD / (alturaD * alturaD) * 10000).toFloat()
        _result.value = kotlin.math.round(resultimc).toString()
        return resultimc
    }


}
