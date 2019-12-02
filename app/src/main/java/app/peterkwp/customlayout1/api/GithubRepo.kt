package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName

class GithubRepo(
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("language") val language: String?)