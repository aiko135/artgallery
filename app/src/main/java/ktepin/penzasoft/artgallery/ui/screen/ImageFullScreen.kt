package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.viewmodel.FullScreenViewModel

@Composable
fun ImageFullScreen(
) {
    val fullScreenViewModel: FullScreenViewModel = viewModel()
    val currentImage = fullScreenViewModel.selectedImage.collectAsState()

    Log.d("Artgallery.UI", "Recompose ImageFullScreen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .background(Color.White))
    }
}