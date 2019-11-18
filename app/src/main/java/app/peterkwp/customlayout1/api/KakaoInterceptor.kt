package app.peterkwp.customlayout1.api

import android.content.Context
import app.peterkwp.customlayout1.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class KakaoInterceptor(val context: Context) : Interceptor {
    var restKey:String = ""

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        restKey = context.getString(R.string.kakao_rest_key)
        val request = original.newBuilder()
            .header("Authorization", "KakaoAK $restKey")
            .build()
        return chain.proceed(request)
    }
}