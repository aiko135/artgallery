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
import ktepin.penzasoft.artgallery.ui.theme.ArtgalleryTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ktepin.penzasoft.artgallery.ui.screen.ImageFullScreen
import ktepin.penzasoft.artgallery.ui.screen.ImagesGreedScreen
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()
            ArtgalleryTheme {
                Log.d("Repository", "Recompose main")
                NavHost(navController = navController, startDestination = "screen_im_grid") {
                    composable("screen_im_grid"){
                        ImagesGreedScreen(viewModel){
                            navController.navigate("screen_im_full")
                        }
                    }
                    composable("screen_im_full"){
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

// koinViewModel из либы koin-compose не работет для инжекта viewModel в @composable
//лучше использовать этот инжектор androidx.lifecycle.viewmodel.compose.viewModel