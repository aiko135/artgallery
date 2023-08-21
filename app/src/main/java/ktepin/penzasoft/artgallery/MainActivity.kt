package ktepin.penzasoft.artgallery

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import ktepin.penzasoft.artgallery.ui.theme.ArtgalleryTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.ui.screen.ImageFullScreen
import ktepin.penzasoft.artgallery.ui.screen.ImagesGreedScreen
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenWidthDp = resources.configuration.screenWidthDp.toFloat()
        val density = resources.displayMetrics.density
        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()
            ArtgalleryTheme {
                Log.d("Repository", "Recompose main")
                NavHost(navController = navController, startDestination = "screen_main") {
                    composable("screen_main"){
                        //SCREEN 1
                        ImagesGreedScreen(viewModel, screenWidthDp, density){
                            navController.navigate("screen_full")
                        }
                    }
                    composable(
                        route="screen_full",
                    ){
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