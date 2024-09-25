package cl.bootcamp.ejercicioindividual9.model

data class Paciente(
    var id: Int = 0,
    var nombre: String = "",
    var sexo: String = "",
    var edad : String = "",
    var peso : String = "",
    var altura : String = ""
)