package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.viewmodel.FullScreenViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import ktepin.penzasoft.artgallery.MainActivity

@Composable
fun ImageFullScreen(
    viewModel: FullScreenViewModel,
    config:MainActivity.DisplayConfig
) {
    val currentImage = viewModel.selectedImage.collectAsState()

    Log.d("Artgallery.UI", "Recompose ImageFullScreen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        currentImage.value?.let {
            ImageContainer(it, config)
        } ?: CircularProgressIndicator()
    }
}

@Composable
fun ImageContainer(image: Image, config: MainActivity.DisplayConfig) {
    val MIN_ZOOM = 1f
    val MAX_ZOOM = 5f
    val scale = remember { mutableStateOf(1f) }
    val offset = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier.graphicsLayer(
            scaleX =  scale.value,
            scaleY =  scale.value,
            translationX = offset.value.x,
            translationY = offset.value.y,
        ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        Log.d("Centroid","[${offset.value.x} ?? ${config.screenWidthDp}, ${offset.value.y} ?? ${config.screenHeightDp}]")
                        if(scale.value*zoom in MIN_ZOOM..MAX_ZOOM)
                            scale.value *= zoom
//                        if (offset.value.x < config.screenWidthDp && offset.value.y < config.screenHeightDp)
                        offset.value += pan * scale.value
                    }
                },
            model = image.urls.full,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }


}