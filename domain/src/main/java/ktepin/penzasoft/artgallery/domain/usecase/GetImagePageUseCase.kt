package ktepin.penzasoft.artgallery.domain.usecase

import kotlinx.coroutines.flow.Flow
import ktepin.penzasoft.artgallery.domain.irepo.ImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

class GetImagePageUseCase(
    private val repo: ImageRepository
) {
    private var _page: Int = 0
    val page: Int
        get() = _page

    fun getNextImagePage(): Flow<ApiRequestResult<List<Image>>> = repo.getImagePage(++_page)

    fun getCurrentImagePage(): Flow<ApiRequestResult<List<Image>>> = repo.getImagePage(_page)
}
