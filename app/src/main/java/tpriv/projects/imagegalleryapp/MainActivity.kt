package tpriv.projects.imagegalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tpriv.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import tpriv.projects.imagegalleryapp.ui.navigation.SetUpNavGraph
import tpriv.projects.imagegalleryapp.ui.theme.ImageGalleyAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            ImageGalleyAppTheme {
                navHostController = rememberNavController()

                val viewModel = hiltViewModel<ImageViewModel>()

                SetUpNavGraph(navHostController,viewModel)


            }
        }
    }
}