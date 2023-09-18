package ktepin.penzasoft.artgallery.domain.irepo

import kotlinx.coroutines.flow.Flow
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

interface ImageRepository {
    suspend fun getImagePage(imagePage:Int): ApiRequestResult<List<Image>>
}