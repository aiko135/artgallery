package ktepin.penzasoft.artgallery.domain.irepo

import kotlinx.coroutines.flow.Flow
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

interface IRepositoryImage {
    fun getImagePage(imagePage:Int): Flow<ApiRequestResult<List<Image>>>
}