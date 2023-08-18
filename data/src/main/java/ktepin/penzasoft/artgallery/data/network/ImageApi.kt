package ktepin.penzasoft.artgallery.data.network

import ktepin.penzasoft.artgallery.domain.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
    @GET("/photos?page={pageNum}&order_by=latest")
    fun getImagePage(@Path("pageNum") pageNumber : String): Call<List<Image>>
}