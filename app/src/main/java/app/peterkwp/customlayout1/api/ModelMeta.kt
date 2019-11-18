package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName

data class ModelMeta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)