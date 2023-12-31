package ktepin.penzasoft.artgallery.domain.model
import com.google.gson.annotations.SerializedName

data class ImageUrls(
    @SerializedName("raw")
    val raw : String,
    @SerializedName("full")
    val full : String,
    @SerializedName("regular")
    val regular : String,
    @SerializedName("small")
    val small : String,
    @SerializedName("thumb")
    val thumb : String,
    @SerializedName("small_s3")
    val small_s3 : String,
)