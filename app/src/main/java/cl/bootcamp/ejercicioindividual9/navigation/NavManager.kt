package cl.bootcamp.ejercicioindividual9.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.bootcamp.ejercicioindividual9.view.Pantalla
import cl.bootcamp.ejercicioindividual9.view.PantallaListaPacientes
import cl.bootcamp.ejercicioindividual9.viewModel.ImcViewModel

@Composable
fun NavManager() {
    val navController = rememberNavController()
    val viewModel : ImcViewModel = ImcViewModel()

    NavHost(
        navController = navController,
        startDestination = "ListaPacientes"
    ) {

        composable("ListaPacientes") {
            PantallaListaPacientes(navController,viewModel)
        }

        composable("imcCalculator") {
            Pantalla(viewModel,navController)
        }
    }
}