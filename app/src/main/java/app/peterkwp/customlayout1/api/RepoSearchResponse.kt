package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName

class RepoSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<GithubRepo>?)