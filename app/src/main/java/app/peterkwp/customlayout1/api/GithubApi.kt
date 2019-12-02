package app.peterkwp.customlayout1.api

import app.peterkwp.customlayout1.feature.AppConst
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET(AppConst.API_GITHUB_SEARCH)
    fun searchRepository(@Query("q") query: String): Observable<RepoSearchResponse>
}