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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import java.lang.Float.max

//custom scale/offset controller with aspect ratio check
class MovableZoomState(private val maxScale: Float) {
    private var _scale = mutableStateOf(1f)
    val scale: Float
        get() = _scale.value

    private var _offset = mutableStateOf(Offset(0f,0f))
    val offset: Offset
        get() = _offset.value

    private var layoutSize = Size.Zero
    fun setLayoutSize(size: Size) {
        layoutSize = size
        updateFitImageSize()
    }

    private var imageSize = Size.Zero
    fun setImageSize(size: Size) {
        imageSize = size
        updateFitImageSize()
    }

    private var fitImageSize = Size.Zero
    private fun updateFitImageSize() {
        if ((imageSize == Size.Zero) || (layoutSize == Size.Zero)) {
            fitImageSize = Size.Zero
            return
        }

        val imageAspectRatio = imageSize.width / imageSize.height
        val layoutAspectRatio = layoutSize.width / layoutSize.height

        fitImageSize = if (imageAspectRatio > layoutAspectRatio) {
            imageSize * (layoutSize.width / imageSize.width)
        } else {
            imageSize * (layoutSize.height / imageSize.height)
        }
    }

    fun applyGesture(pan: Offset, zoom: Float) {
        _scale.value = (_scale.value * zoom).coerceIn(1f, maxScale)

        val boundX = max((fitImageSize.width * _scale.value - layoutSize.width), 0f) / 2f
        val x = (_offset.value.x + pan.x).coerceIn(-boundX, boundX)

        val boundY = max((fitImageSize.height * _scale.value - layoutSize.height), 0f) / 2f
        val y = (_offset.value.y + pan.y).coerceIn(-boundY, boundY)

        _offset.value = Offset(x,y);
    }
}

@Composable
fun ImageFullScreen(
    viewModel: FullScreenViewModel,
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
            ImageContainer(it)
        } ?: CircularProgressIndicator()
    }
}

@Composable
fun ImageContainer(image: Image) {
    val zoomState = remember { MovableZoomState(5f) }
    zoomState.setImageSize(Size(image.width.toFloat(), image.height.toFloat()))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    zoomState.applyGesture(pan = pan, zoom = zoom)
                }
            },
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = zoomState.scale,
                    scaleY = zoomState.scale,
                    translationX = zoomState.offset.x,
                    translationY = zoomState.offset.y
                )
                .onSizeChanged{
                    zoomState.setLayoutSize(it.toSize())
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = image.urls.full,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}