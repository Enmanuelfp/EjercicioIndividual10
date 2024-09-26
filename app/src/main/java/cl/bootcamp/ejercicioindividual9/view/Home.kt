package cl.bootcamp.ejercicioindividual9.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.bootcamp.ejercicioindividual9.R
import cl.bootcamp.ejercicioindividual9.component.AlertError
import cl.bootcamp.ejercicioindividual9.component.Spacio
import cl.bootcamp.ejercicioindividual9.viewModel.ImcViewModel

@Composable
fun Pantalla(viewModel: ImcViewModel, navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitulo()
        Spacio()
        MultiBtn { sexoSeleccionado ->
            viewModel.sexo.value = sexoSeleccionado
        }
        Spacio()
        TexEdad(viewModel.edad.value) { viewModel.edad.value = it }
        Spacio()
        TexPeso(viewModel.peso.value) { viewModel.peso.value = it }
        Spacio()
        TexAltura(viewModel.altura.value) { viewModel.altura.value = it }
        BtnCalcular {
            viewModel.result.value =
                viewModel.Calculo(
                    (viewModel.peso.value),
                    viewModel.altura.value,
                    viewModel.edad.value,
                    viewModel.sexo.value
                ).toString()
        }
        Spacio()
        TexResult(viewModel, navController)
    }
}


@Composable
fun TextTitulo() {
    Text(
        text = stringResource(id = R.string.calculadora),
        fontSize = 40.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        textAlign = TextAlign.Center

    )
}


@Composable
fun BtnCalcular(onClick: () -> Unit) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.hsl(241f, 0.43f, 0.27f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)

    )
    {
        Text(
            text = stringResource(id = R.string.calcular),

            )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiBtn(onSexoChange: (String) -> Unit) {
    val sexo = remember { mutableStateListOf<Int>() }
    val sexoOptions =
        listOf(stringResource(id = R.string.sexo), stringResource(id = R.string.sexo2))

    MultiChoiceSegmentedButtonRow {
        sexoOptions.forEachIndexed { posicion, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = posicion,
                    count = sexoOptions.size
                ),
                onCheckedChange = {
                    if (sexo.isEmpty()) {
                        sexo.add(posicion)
                        onSexoChange(sexoOptions[posicion]) // Actualiza el ViewModel
                    } else {
                        if (posicion in sexo) {
                            sexo.remove(posicion)
                            onSexoChange("") // Si se deselecciona, limpiamos el valor en el ViewModel
                        } else {
                            sexo.clear()
                            sexo.add(posicion)
                            onSexoChange(sexoOptions[posicion]) // Actualiza el ViewModel
                        }
                    }
                },
                checked = posicion in sexo
            ) {
                Text(label)
            }
        }
    }
}


@Composable
fun TexEdad(edad: String, onEdadChange: (String) -> Unit) {
    OutlinedTextField(
        value = edad,
        onValueChange = onEdadChange,
        placeholder = { Text(text = stringResource(id = R.string.edad)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


@Composable
fun TexPeso(peso: String, onPesoChange: (String) -> Unit) {
    OutlinedTextField(
        value = peso,
        onValueChange = onPesoChange,
        placeholder = { Text(text = stringResource(id = R.string.peso)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}


@Composable
fun TexAltura(altura: String, onAlturaChange: (String) -> Unit) {

    OutlinedTextField(
        value = altura,
        onValueChange = onAlturaChange,
        placeholder = { Text(text = stringResource(id = R.string.altura)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


@Composable
fun TexResult(viewModel: ImcViewModel, navController: NavController) {
    val hasCalculated = viewModel.hasCalculated.value
    if (hasCalculated) {
        AlertError(
            stringResource(R.string.error),
            stringResource(R.string.camposrequeridos),
            stringResource(R.string.entendido),
            onConfirmClick = { viewModel.hasCalculated.value = false },
            onDismissClick = { viewModel.hasCalculated.value = false }
        )
    } else {
        Text(
            text = viewModel.result.value,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = viewModel.estadoSalud.value,
            fontSize = 35.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center
        )
        if (viewModel.result.value.isNotEmpty() && viewModel.result.value != "0.0") {
            Button(onClick = {

                viewModel.ActualizarPaciente(
                    nombre = viewModel.nombre.value,
                    edad = viewModel.edad.value,
                    sexo = viewModel.sexo.value,
                    imc = viewModel.result.value,
                    estadoSalud = viewModel.estadoSalud.value
                )
                navController.navigate("listaPacientes")
            }) {
                Text(text = stringResource(R.string.guardar))
            }
        }
    }
}









