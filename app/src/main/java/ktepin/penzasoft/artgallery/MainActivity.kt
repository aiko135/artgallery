package ktepin.penzasoft.artgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ktepin.penzasoft.artgallery.ui.theme.ArtgalleryTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtgalleryTheme {

                Column(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Card{
                        Greeting("Android")
                        Greeting("dev")
                    }

                }
            }

//            Greeting("Hellow")
//            ArtgalleryTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Column{
//                        Greeting("Android")
//                        Greeting("dev")
//                    }
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtgalleryTheme {
        Greeting("world")
    }
}

// koinViewModel из либы koin-compose не работет для инжекта viewModel в @composable
//лучше использовать этот инжектор androidx.lifecycle.viewmodel.compose.viewModel