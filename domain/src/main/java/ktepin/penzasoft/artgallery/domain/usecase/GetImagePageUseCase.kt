package ktepin.penzasoft.artgallery.domain.usecase

import kotlinx.coroutines.flow.Flow
import ktepin.penzasoft.artgallery.domain.irepo.IRepositoryImage
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

class GetImagePageUseCase(
    private val repo: IRepositoryImage
) {
    fun getImagePage(imagePage:Int): Flow<ApiRequestResult<List<Image>>> = repo.getImagePage(imagePage)
}
