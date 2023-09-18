package ktepin.penzasoft.artgallery.domain.usecase

import kotlinx.coroutines.flow.Flow
import ktepin.penzasoft.artgallery.domain.irepo.ImageRepository
import ktepin.penzasoft.artgallery.domain.model.ApiRequestResult
import ktepin.penzasoft.artgallery.domain.model.Image

//better to name it "interactor"
class GetImagePageUseCase(
    private val repo: ImageRepository
) {
    private var _page: Int = 0
    val page: Int
        get() = _page

    suspend fun getNextImagePage(): ApiRequestResult<List<Image>> = repo.getImagePage(++_page)

    suspend fun getCurrentImagePage(): ApiRequestResult<List<Image>> = repo.getImagePage(_page)
}
