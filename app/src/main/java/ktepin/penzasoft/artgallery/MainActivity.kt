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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ktepin.penzasoft.artgallery.ui.theme.ArtgalleryTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.domain.model.RequestError
import ktepin.penzasoft.artgallery.domain.model.RequestSuccess
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

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
fun Greeting(name: String, viewModel: MainViewModel = viewModel() ) {
    var page: Int by remember { mutableStateOf(0) }
    val data = viewModel.imageStateFlow.collectAsState()

//    LaunchedEffect(Unit) {
//
//    }
    var text = "TMP"
    if (data.value is RequestSuccess<*>){
        val a = data.value as RequestSuccess<List<Image>>
        text = a.entity.size.toString()
    }
    Log.d("Repository", "Recompose")
    Column {
        Button(
            onClick = {
                page ++
                Log.d("Repository", page.toString())
            },
            colors=ButtonDefaults.buttonColors()
        ){
            Text(
                "Button"
            )
        }
        Text(
            text
        )
        Text(
            page.toString()
        )
    }
//    Text(text = "Hello $name!")
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