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
import ktepin.penzasoft.artgallery.ui.screen.ImagesGreedScreen
import ktepin.penzasoft.artgallery.viewmodel.FullScreenViewModel
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

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
            val mainViewModel: MainViewModel = viewModel()
            val fullScreenViewModel:FullScreenViewModel = viewModel()
            val navController = rememberNavController()
            ArtgalleryTheme {
                Log.d("Artgallery.UI", "Recompose main")
                NavHost(navController = navController, startDestination = "screen_main") {
                    composable("screen_main"){
                        //SCREEN 1
                        ImagesGreedScreen(mainViewModel, config){
                            fullScreenViewModel.updateSelectedImage(it)
                            navController.navigate("screen_full")
                        }
                    }
                    composable("screen_full"){
                        //SCREEN 2
                        ImageFullScreen(fullScreenViewModel, config)
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