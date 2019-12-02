package app.peterkwp.customlayout1.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class GithubInterceptor(private val key: String): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "token $key")
            .build()
        return chain.proceed(request)
    }
}