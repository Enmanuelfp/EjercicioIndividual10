package cl.bootcamp.ejercicioindividual9.model

data class Paciente(
    var nombre: String = "",
    var edad : String = "",
    var sexo: String = "",
    var imc: String = "",
    var estadoSalud: String = "",
    var imcCalculado: Boolean = false
)