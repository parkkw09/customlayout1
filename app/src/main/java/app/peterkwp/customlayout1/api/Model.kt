package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("meta") val meta: ModelMeta,
    @SerializedName("documents") val documents: List<ModelDocuments>
)