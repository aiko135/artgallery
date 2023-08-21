package ktepin.penzasoft.artgallery.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ktepin.penzasoft.artgallery.domain.model.Image

class FullScreenViewModel:ViewModel() {

    private val _selectedImage = MutableStateFlow<Image?>(null)
    val selectedImage: StateFlow<Image?> = _selectedImage.asStateFlow()

    fun updateSelectedImage(image: Image){
        _selectedImage.update {
            image
        }
    }

}