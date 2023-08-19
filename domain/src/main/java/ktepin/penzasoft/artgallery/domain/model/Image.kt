package ktepin.penzasoft.artgallery.domain.model
import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("id")
    val id : String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("urls")
    val urls: ImageUrls,
    @SerializedName("links")
    val links: ImageLinks,
)