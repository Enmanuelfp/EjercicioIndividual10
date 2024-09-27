package cl.bootcamp.ejercicioindividual9.view.onBoarding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.pager.rememberPagerState

import cl.bootcamp.ejercicioindividual9.R
import cl.bootcamp.ejercicioindividual9.model.PageData
import cl.bootcamp.ejercicioindividual9.model.StoreBoarding
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainOnBoarding(navController: NavController, store: StoreBoarding){
    val items = ArrayList<PageData>()
    items.add(
        PageData(
            R.raw.saludo,
            stringResource(R.string.saludo),
            stringResource(R.string.descipcionimc)
        )
    )
    items.add(
        PageData(
            R.raw.intermedio,
            stringResource(R.string.intermedio),
            stringResource(R.string.descipcionintermedio)
        )
    )
    items.add(
        PageData(
            R.raw.adios,
            stringResource(R.string.finalpantalla),
            stringResource(R.string.finalpantalladescripcion)
        )
    )

    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        navController,
        store
    )
}