package ktepin.penzasoft.artgallery.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktepin.penzasoft.artgallery.data.network.ImageApi
import ktepin.penzasoft.artgallery.domain.irepo.IRepositoryImage
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

open class ImageRepository(
    private val api:ImageApi
) : AbstractRepository(), IRepositoryImage{

    override val tag: String = "ImagePerository"

     override fun getImagePage(imagePage:Int): Flow<ApiRequestResult<List<Image>>> = flow {
         val result = executeRequest(api.getImagePage(imagePage.toString()))
         emit(result)
    }.flowOn(Dispatchers.IO)

}