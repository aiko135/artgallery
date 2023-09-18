package ktepin.penzasoft.artgallery

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ktepin.penzasoft.artgallery.ui.theme.ArtgalleryTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ktepin.penzasoft.artgallery.ui.screen.ImageFullScreen
import ktepin.penzasoft.artgallery.ui.screen.ImagesGridScreen
import ktepin.penzasoft.artgallery.viewmodel.FullScreenViewModel
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    data class DisplayConfig(val screenHeightDp:Float, val screenWidthDp:Float, val density:Float)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = DisplayConfig(
            resources.configuration.screenHeightDp.toFloat(),
            resources.configuration.screenWidthDp.toFloat(),
            resources.displayMetrics.density
        )

        setContent {
            //inject as State by Compose
            //val mainViewModel: MainViewModel = viewModel()
            //val fullScreenViewModel:FullScreenViewModel = viewModel()

            //inject by Koin + Compose
            val fullScreenViewModel = koinViewModel<FullScreenViewModel>()

            val navController = rememberNavController()
            ArtgalleryTheme {
                Log.d("Artgallery.UI", "Recompose main")
                NavHost(navController = navController, startDestination = "screen_main") {
                    composable("screen_main"){
                        //SCREEN 1
                        ImagesGridScreen(displayConfig = config){
                            fullScreenViewModel.updateSelectedImage(it)
                            navController.navigate("screen_full")
                        }
                    }
                    composable("screen_full"){
                        //SCREEN 2
                        ImageFullScreen()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtgalleryTheme {
       Text("world")
    }
}