package ktepin.penzasoft.artgallery.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ktepin.penzasoft.artgallery.MainActivity
import ktepin.penzasoft.artgallery.R
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.ui.screen.GridConfig.ELEMENTS_LEFT_TO_UPDATE
import ktepin.penzasoft.artgallery.ui.theme.PurpleGrey80
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

object GridConfig {
    //number of images left to display that triggers new page load
    const val ELEMENTS_LEFT_TO_UPDATE = 10
}

@Composable
fun ImagesGridScreen(
    viewModel: MainViewModel,
    displayConfig: MainActivity.DisplayConfig,
    onImageSelect: (im:Image) -> Unit
) {
    val imagesCollected = viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyStaggeredGridState()

    Log.d("Artgallery.UI", "Recompose ImagesGreedScreen")
    LaunchedEffect(lazyGridState.firstVisibleItemIndex) {
        //Effect launched after every scroll step
        if (!imagesCollected.value.imageGroupError){
            //init new page loading only if no error
            if (imagesCollected.value.images.size - lazyGridState.firstVisibleItemIndex < ELEMENTS_LEFT_TO_UPDATE){
                viewModel.loadNextPage()
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        if(imagesCollected.value.imageGroupError){
            ErrorHeader()
        }
        LazyVerticalStaggeredGrid(
            state = lazyGridState,
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
        ) {
            items(imagesCollected.value.images) { //TODO key = {it.id}
                GridItem(
                    image = it,
                    onImageSelect=onImageSelect,
                    screenWidthDp=displayConfig.screenWidthDp,
                    density=displayConfig.density)
            }
            item { Spacer(500) }
            item { Spacer(500) }
        }
    }
}

@Composable
fun GridItem(
    image: Image,
    onImageSelect: (im:Image) -> Unit,
    screenWidthDp: Float,
    density: Float
) {
    //manual fixed container size calculation. Lag reduce
    val paddingDp = 4
    val containerWidthDp = screenWidthDp / 2 - paddingDp * 4

    val imWidthDp:Float = image.width / density
    val imHeightDp:Float = image.height / density
    val containerHeightDp =  imHeightDp * containerWidthDp / imWidthDp
    Box(
        modifier = Modifier
            .width(Dp(containerWidthDp)) //Container should be fixed otherwise fillMaxSize() multiple rerenders will produce lags
            .height(Dp(containerHeightDp))
            .padding(paddingDp.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable { onImageSelect.invoke(image) }
            .background(PurpleGrey80),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = image.urls.small,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun Spacer(spacerHeightDp: Int) {
    Box(
        modifier = Modifier
            .height(Dp(spacerHeightDp.toFloat()))
            .fillMaxWidth(),
    )
}

@Composable
fun ErrorHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ){
         Row(verticalAlignment = Alignment.CenterVertically){
             Text(
                 text = stringResource(R.string.network_error),
                 textAlign = TextAlign.Center
             )
            CircularProgressIndicator()
        }
    }
}