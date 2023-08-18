package ktepin.penzasoft.artgallery.domain.model

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("self")
    val self : String,
    @SerializedName("html")
    val html : String,
    @SerializedName("download")
    val download : String,
    @SerializedName("download_location")
    val downloadLocation : String,
)