package ktepin.penzasoft.artgallery.domain.model
import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("id")
    val id : String,
    @SerializedName("id")
    val width: Int,
    @SerializedName("id")
    val height: Int,
    @SerializedName("urls")
    val urls: ImageUrls,
    @SerializedName("links")
    val links: ImageLinks,
)