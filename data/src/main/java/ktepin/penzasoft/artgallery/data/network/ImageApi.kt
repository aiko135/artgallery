package ktepin.penzasoft.artgallery.data.network

import ktepin.penzasoft.artgallery.domain.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {
    @GET("/photos")
    fun getImagePage(@Query("page") pageNumber : String): Call<List<Image>>
}