package cl.bootcamp.ejercicioindividual9.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.bootcamp.ejercicioindividual9.R
import cl.bootcamp.ejercicioindividual9.component.Spacio
import cl.bootcamp.ejercicioindividual9.model.Paciente
import cl.bootcamp.ejercicioindividual9.viewModel.ImcViewModel

@Composable
fun PantallaListaPacientes(navController: NavController, viewModel: ImcViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBarListaPacientes()
        if (viewModel.pacientes.isNotEmpty()) {
            LazyColumn {
                items(viewModel.pacientes) { paciente ->
                    CardPaciente(navController, paciente, viewModel)
                }
            }
        }

    }
    BtnAgregar(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarListaPacientes() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.listaPacientes),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color(0xFF3A3ADF))
    )
}


@Composable
fun CardPaciente(
    navController: NavController,
    paciente: Paciente,
    viewModel: ImcViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        {
            if (!paciente.imcCalculado) {
                Text(
                    text = paciente.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacio()
                Button(
                    onClick = {
                        viewModel.nombre.value = paciente.nombre
                        viewModel.LimpiarCampos()
                            navController.navigate("Home")
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Black
                    )
                )
                {
                    Text(stringResource(R.string.calcularimc))
                }
            } else {
                Text(
                    text = "Nombre: ${paciente.nombre}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Edad: ${paciente.edad}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Sexo: ${paciente.sexo}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "IMC: ${paciente.imc}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Estado de Salud: ${paciente.estadoSalud}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }


        }
    }
}


@Composable
fun BtnAgregar(navController: NavController, viewModel: ImcViewModel) {
    var showModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = { showModal = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Red
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Nombre",
                tint = Color.White
            )
        }

        if (showModal) {
            ModalAgregarPaciente(
                onDismiss = { showModal = false },
                onConfirm = { nombrePaciente ->
                    viewModel.AgregarPaciente(nombrePaciente)
                    showModal = false
                    navController.navigate("ListaPacientes")
                }
            )
        }
    }
}

@Composable
fun ModalAgregarPaciente(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var nombrePaciente by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.agregar))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = nombrePaciente,
                    onValueChange = { nombrePaciente = it },
                    label = { Text(stringResource(R.string.nombre)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(nombrePaciente)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.agregar))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.cancelar))
            }
        }
    )
}






