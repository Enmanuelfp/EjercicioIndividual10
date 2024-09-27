package cl.bootcamp.ejercicioindividual9.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.bootcamp.ejercicioindividual9.model.StoreBoarding
import cl.bootcamp.ejercicioindividual9.view.MainOnBoarding
import cl.bootcamp.ejercicioindividual9.view.Pantalla
import cl.bootcamp.ejercicioindividual9.view.PantallaListaPacientes
import cl.bootcamp.ejercicioindividual9.view.SplashScreen
import cl.bootcamp.ejercicioindividual9.viewModel.ImcViewModel

@Composable
fun NavManager() {
    val navController = rememberNavController()
    val viewModel : ImcViewModel = ImcViewModel()
    val context = LocalContext.current
    val dataStore = StoreBoarding(context)
    val store = dataStore.getBoarding.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (store.value)"ListaPacientes" else "Splash"
    ) {

        composable("Bienvenida"){
            MainOnBoarding(navController,dataStore)
        }

        composable("ListaPacientes") {
            PantallaListaPacientes(navController,viewModel)
        }

        composable("Home") {
            Pantalla(viewModel,navController)
        }

        composable("Splash"){
            SplashScreen(navController,store.value)
        }
    }
}