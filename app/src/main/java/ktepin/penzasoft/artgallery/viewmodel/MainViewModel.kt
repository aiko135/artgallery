package ktepin.penzasoft.artgallery.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    private val imageRepo:ImageRepository by inject(LocalImageRepository::class.java)

    data class UiState(var listSize:Int, var images: MutableList<Image>, var imageGroupError:Boolean)

    private val _uiState = MutableStateFlow(UiState(0, mutableListOf(),false))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPage(1);
    }

    fun loadPage(page:Int){
        viewModelScope.launch {
             imageRepo.getImagePage(page).collect{
                when(it){
                    is RequestSuccess -> {
                        _uiState.value.images.addAll(it.entity)
                    }
                    is RequestError -> {
                        _uiState.value.imageGroupError = true
                    }
                }
                 //Recreate UiState inner fields to trigger recompose
                 _uiState.value =  UiState(_uiState.value.images.size, _uiState.value.images, _uiState.value.imageGroupError)
            }
        }
    }
}