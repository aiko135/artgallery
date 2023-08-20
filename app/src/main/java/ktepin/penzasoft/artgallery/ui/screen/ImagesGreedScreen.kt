package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImagesGreedScreen(viewModel: MainViewModel, onImageSelect: () -> Unit) {
    var page: Int by remember { mutableStateOf(1) }
    val imagesCollected = viewModel.uiState.collectAsState()

    Log.d("Repository", "Recompose ImagesGreedScreen")
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Column {
                Button(
                    onClick = {
                        page++
                        viewModel.loadPage(page)
                        Log.d("Repository", page.toString())
                    },
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(
                        "Button"
                    )
                }
                Text("${page.toString()}")
            }
        }
        GlideImage(
            model = imagesCollected.value.images[page].links.download,
            contentDescription = "test"
        )
    }
}