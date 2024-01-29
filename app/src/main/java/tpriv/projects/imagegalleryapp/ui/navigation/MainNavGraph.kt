package tpriv.projects.imagegalleryapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import tpriv.projects.imagegalleryapp.R
import tpriv.projects.imagegalleryapp.ui.screens.DetailScreen
import tpriv.projects.imagegalleryapp.ui.screens.HomeScreen
import tpriv.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import tpriv.projects.imagegalleryapp.util.ConnectionState
import tpriv.projects.imagegalleryapp.util.connectivityState

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController,
    viewModel: ImageViewModel
) {

    val scaffoldState = rememberScaffoldState()

    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    val context = LocalContext.current

    val filter1 = viewModel.searchImagePager1.collectAsLazyPagingItems()
    val filter2 = viewModel.searchImagePager2.collectAsLazyPagingItems()

    LaunchedEffect(key1 = isConnected) {

        filter1.refresh()
        filter2.refresh()

        if (!isConnected) {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(R.string.offline_text),
                context.getString(R.string.dismiss).uppercase(),
                SnackbarDuration.Long
            )
        } else {

            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(bottomBar = { BottomNavBar(navHostController) }, scaffoldState = scaffoldState) {

        NavHost(
            navController = navHostController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(),
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(
                    filter1 = filter1,
                    filter2 = filter2,
                    viewModel = viewModel,
                    navHostController = navHostController,)
            }
            composable(route = Screens.Details.route) {
                DetailScreen(
                    navHostController, viewModel
                )
            }


        }
    }

}


@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
) {
    val screens = listOf(
        Screens.Home,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation{
        screens.forEach {
        }

    }

}

@Composable
fun TopBar(
    title: String,
    onBackPressed: (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {

    TopAppBar(
        title = {
            Text(text = title, style = TextStyle(fontSize = 25.sp))
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Rounded.ArrowBack, stringResource(R.string.back_icon))
                }
            }
        } else null,
        contentColor = Color.White,
        elevation = 10.dp,
        actions = {
            if (actions != null) {
                actions()
            }
        },

        )
}