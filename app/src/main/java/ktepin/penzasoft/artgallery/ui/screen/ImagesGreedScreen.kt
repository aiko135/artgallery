package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

@Composable
fun ImagesGreedScreen(onImageSelect: ()->Unit){
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

@Composable
fun Greeting(name: String, viewModel: MainViewModel = viewModel()) {
    var page: Int by remember { mutableStateOf(0) }
    var text = "TMP"
    Log.d("Repository", "Recompose")
    Column {
        Button(
            onClick = {
                page++
                Log.d("Repository", page.toString())
            },
            colors = ButtonDefaults.buttonColors()
        ) {
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