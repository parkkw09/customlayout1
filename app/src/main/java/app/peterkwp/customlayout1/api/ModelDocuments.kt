package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName

data class ModelDocuments(
    @SerializedName("collection") val collection: String?,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("width") val width: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("display_sitename") val displaySiteName: String?,
    @SerializedName("doc_url") val docUrl: String?,
    @SerializedName("datetime") val dateTime: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("contents") val contents: String?,
    @SerializedName("url") val url: String?
)