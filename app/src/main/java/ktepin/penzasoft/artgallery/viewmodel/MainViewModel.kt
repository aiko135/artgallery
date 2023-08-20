package ktepin.penzasoft.artgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ktepin.penzasoft.artgallery.data.repository.ImageRepository
import ktepin.penzasoft.artgallery.data.repository.LocalImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image
import ktepin.penzasoft.artgallery.domain.model.RequestError
import org.koin.java.KoinJavaComponent.inject

class MainViewModel : ViewModel() {
    private val imageRepo:ImageRepository by inject(LocalImageRepository::class.java)

    fun loadPage(page:Int){
        viewModelScope.launch {
            val result = imageRepo.getImagePage(page).collect()
        }
    }
}