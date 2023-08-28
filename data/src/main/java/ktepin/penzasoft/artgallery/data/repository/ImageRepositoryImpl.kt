package ktepin.penzasoft.artgallery.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ktepin.penzasoft.artgallery.data.network.ImageApi
import ktepin.penzasoft.artgallery.domain.irepo.ImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

open class ImageRepositoryImpl(
    private val api: ImageApi
) : AbstractRepository(), ImageRepository {

    override val tag: String = "Artgallery.Repository"

    override fun getImagePage(imagePage: Int): Flow<ApiRequestResult<List<Image>>> = flow {
        Log.d(tag, "HTTP REQUEST for page $imagePage")
        val result = executeRequest(api.getImagePage(imagePage.toString()))
        emit(result)
    }

}