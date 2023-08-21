package ktepin.penzasoft.artgallery.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ktepin.penzasoft.artgallery.data.repository.ImageRepository
import ktepin.penzasoft.artgallery.data.repository.LocalImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.domain.model.RequestError
import ktepin.penzasoft.artgallery.domain.model.RequestSuccess
import org.koin.java.KoinJavaComponent.inject
import java.util.Random

class MainViewModel : ViewModel() {
    private val imageRepo: ImageRepository by inject(LocalImageRepository::class.java)

    data class UiState(
        var prevPage: Int,
        var images: MutableList<Image>,
        var imageGroupError: Boolean
    )

    private val _uiState = MutableStateFlow(UiState(0, mutableListOf(), false))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPage(1);
    }

    fun loadPage(page: Int) {
        if (page != _uiState.value.prevPage) {
            viewModelScope.launch(Dispatchers.IO) {
                imageRepo.getImagePage(page).collect {
                    when (it) {
                        is RequestSuccess -> {
                            _uiState.value.images.addAll(it.entity)
                        }

                        is RequestError -> {
                            _uiState.value.imageGroupError = true
                        }
                    }
                    //Invoke to all listeners to trigger recompose.
                    _uiState.update { currentState ->
                        UiState(page, currentState.images, currentState.imageGroupError)
                    }
                    //Alternative: _uiState.value = UiState(page, _uiState.value.images, _uiState.value.imageGroupError)
                }
            }
        }
    }
}