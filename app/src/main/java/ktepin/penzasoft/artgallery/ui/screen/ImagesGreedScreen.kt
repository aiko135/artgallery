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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.ui.screen.GridConfig.ELEMENTS_LEFT_TO_UPDATE
import ktepin.penzasoft.artgallery.viewmodel.MainViewModel

object GridConfig{
    //number of images left to display that triggers new page load
    const val ELEMENTS_LEFT_TO_UPDATE = 6
}

@Composable
fun ImagesGreedScreen(viewModel: MainViewModel, onImageSelect: () -> Unit) {

    var page: Int by remember { mutableStateOf(1) }
    val imagesCollected = viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyStaggeredGridState()

    Log.d("Repository", "Recompose ImagesGreedScreen")

    LaunchedEffect(lazyGridState.firstVisibleItemIndex){
        //Effect launched after every scroll step
        //Also recompose after every effect state change

//        Log.d("Repository",lazyGridState.firstVisibleItemIndex.toString())
        if (imagesCollected.value.images.size - lazyGridState.firstVisibleItemIndex < ELEMENTS_LEFT_TO_UPDATE){
            page++
        }
    }

    LaunchedEffect(page){
        viewModel.loadPage(page)
    }


    LazyVerticalStaggeredGrid(
        state = lazyGridState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(imagesCollected.value.images){
            GridItem(image = it)
        }
    }



//    Column(
//        modifier = Modifier
//            .background(Color.LightGray)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card {
//            Column {
//                Button(
//                    onClick = {
//                        page++
//                        viewModel.loadPage(page)
//                        Log.d("Repository", page.toString())
//                    },
//                    colors = ButtonDefaults.buttonColors()
//                ) {
//                    Text(
//                        "Button"
//                    )
//                }
//                Text("${page.toString()}")
//            }
//        }
//        AsyncImage(
//            model = imagesCollected.value.images[0].links.download,
//            contentDescription = null,
//        )
//    }
}

@Composable
fun GridItem(image:Image){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Green)
    ){
        AsyncImage(
            model = image.urls.small,
            contentDescription = null,
        )
    }
}

//@Composable
//fun Header(){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.Center
//    ){
//        Text(text = "Unsplash pictures gallery")
//    }
//}