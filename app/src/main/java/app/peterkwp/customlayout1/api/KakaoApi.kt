package app.peterkwp.customlayout1.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApi {

    @GET("/v2/search/image")
    fun searchImage(@Query("query") query: String,
                    @Query("page") page: String = "1",
                    @Query("size") size: String = "10"): Observable<Model>
}