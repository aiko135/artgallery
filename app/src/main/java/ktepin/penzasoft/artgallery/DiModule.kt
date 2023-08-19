package ktepin.penzasoft.artgallery
import ktepin.penzasoft.artgallery.data.network.ImageApi
import ktepin.penzasoft.artgallery.data.network.RetrofitClient
import ktepin.penzasoft.artgallery.data.repository.ImageRepository
import ktepin.penzasoft.artgallery.data.repository.LocalImageRepository
import ktepin.penzasoft.artgallery.domain.usecase.GetImagePageUseCase
import org.koin.dsl.module

val diModule = module{

    single<LocalImageRepository>{
        val api = RetrofitClient.getTypedRetrofitInstance(ImageApi::class.java)
        LocalImageRepository(api)
    }

    single<GetImagePageUseCase> {
        GetImagePageUseCase(get<LocalImageRepository>())
    }
}