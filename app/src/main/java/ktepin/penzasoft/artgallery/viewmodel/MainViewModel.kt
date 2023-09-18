package ktepin.penzasoft.artgallery.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.domain.model.RequestError
import ktepin.penzasoft.artgallery.domain.model.RequestSuccess
import ktepin.penzasoft.artgallery.domain.usecase.GetImagePageUseCase
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(private val getImagePageUseCase: GetImagePageUseCase) : ViewModel() {

    data class UiState(
        var prevPage: Int,
        var images: MutableList<Image>,
        var imageGroupError: Boolean
    )

    private val _uiState = MutableStateFlow(UiState(-1, mutableListOf(), false))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun loadNextPage() {
        val currentPage = getImagePageUseCase.page;
        if (currentPage != _uiState.value.prevPage || _uiState.value.imageGroupError) {
            viewModelScope.launch(Dispatchers.IO) {
                getImagePageUseCase.getNextImagePage().collect {
                    when (it) {
                        is RequestSuccess -> {
                            Log.d("Artgallery.VM", "success")
                            _uiState.value.images.addAll(it.entity)
                            _uiState.update { currentState ->
                                UiState(currentPage, currentState.images, false)
                            }
                        }

                        is RequestError -> {
                            Log.d("Artgallery.VM", "fail")
                            _uiState.update { currentState ->
                                UiState(currentPage, currentState.images, true)
                            }
                            delay(2000) //wait
                            getImagePageUseCase.getCurrentImagePage() //try again
                        }
                    }
                }
            }
        }
    }
}