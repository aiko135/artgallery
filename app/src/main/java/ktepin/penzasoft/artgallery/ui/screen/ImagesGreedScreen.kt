package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.ui.screen.GridConfig.ELEMENTS_LEFT_TO_UPDATE
import ktepin.penzasoft.artgallery.ui.theme.Purple80
import ktepin.penzasoft.artgallery.ui.theme.PurpleGrey80
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

object GridConfig {
    //number of images left to display that triggers new page load
    const val ELEMENTS_LEFT_TO_UPDATE = 10
}

@Composable
fun ImagesGreedScreen(
    viewModel: MainViewModel,
    screenWidthDp: Float,
    density:Float,
    onImageSelect: () -> Unit
) {
    var page: Int by remember { mutableStateOf(1) }
    val imagesCollected = viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyStaggeredGridState()

    Log.d("Repository", "Recompose ImagesGreedScreen")

    LaunchedEffect(lazyGridState.firstVisibleItemIndex) {
        //Effect launched after every scroll step
        if (imagesCollected.value.images.size - lazyGridState.firstVisibleItemIndex < ELEMENTS_LEFT_TO_UPDATE){
            page++
            viewModel.loadPage(page)
        }
    }


    LazyVerticalStaggeredGrid(
        state = lazyGridState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(imagesCollected.value.images) {
            GridItem(image = it, screenWidthDp=screenWidthDp, density=density)
        }
        item { Spacer(500) }
        item { Spacer(500) }
    }
}

@Composable
fun GridItem(image: Image, screenWidthDp: Float, density: Float) {
    //Pre image Container size calculation. Lag reduce
    val padding = 4
    val containerWidthDp = screenWidthDp / 2 - padding * 4
    val containerHeightDp = itemHeightDp(image, containerWidthDp, density)
    Box(
        modifier = Modifier
            .width(Dp(containerWidthDp)) //Container should be fixed otherwise fillMaxSize() multiple rerenders will produce lags
            .height(Dp(containerHeightDp))
            .padding(padding.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Purple80),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = image.urls.small,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

fun itemHeightDp(im:Image, containerWidthDp:Float, density: Float):Float {
    val imWidthDp:Float = im.width / density
    val imHeightDp:Float = im.height / density
    return imHeightDp * containerWidthDp / imWidthDp
}

@Composable
fun Spacer(spacerHeightDp: Int) {
    Row(
        modifier = Modifier
            .background(PurpleGrey80)
            .height(Dp(spacerHeightDp.toFloat()))
            .fillMaxWidth(),
    ) {}
}