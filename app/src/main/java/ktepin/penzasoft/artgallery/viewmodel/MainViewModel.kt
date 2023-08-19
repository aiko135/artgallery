package ktepin.penzasoft.artgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import ktepin.penzasoft.artgallery.data.repository.ImageRepository
import ktepin.penzasoft.artgallery.data.repository.LocalImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.domain.model.RequestError
import org.koin.java.KoinJavaComponent.inject

class MainViewModel : ViewModel() {
    private val imageRepo:ImageRepository by inject(LocalImageRepository::class.java)

    private var _sharedFlow = MutableStateFlow<ApiRequestResult<List<Image>>>(RequestError("test",1))
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun loadPage(page:Int){

    }

    init {
        _sharedFlow = imageRepo.getImagePage(1).shareIn(viewModelScope,
            SharingStarted.Lazily,
            0) as MutableStateFlow<ApiRequestResult<List<Image>>>
    }

//    private var _imageStateFlow = imageRepo
//        .getImagePage(1)
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Lazily,
//            0
//        )
//
//    val imageStateFlow = _imageStateFlow




}