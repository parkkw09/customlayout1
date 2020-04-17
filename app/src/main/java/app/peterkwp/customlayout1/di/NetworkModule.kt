package app.peterkwp.customlayout1.di

import android.content.Context
import android.util.Log
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.api.GithubInterceptor
import app.peterkwp.customlayout1.api.KakaoInterceptor
import app.peterkwp.customlayout1.feature.AppConst
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                var parseMessage = message
                Log.d(App.TAG, parseMessage)
                if (parseMessage.contains("END")) {
                    Log.d(App.TAG, "\n")
                    parseMessage += "\n"
                }
            })
        return logger.apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Named(AppConst.KAKAO_API)
    fun provideKakaoInterceptor1(@Named("appContext") ctx: Context): KakaoInterceptor {
        return KakaoInterceptor(ctx.getString(R.string.kakao_rest_key))
    }

    @Provides
    @Named(AppConst.KAKAO_API)
    fun provideOkHttpClient1(loggingInterceptor: HttpLoggingInterceptor, @Named(AppConst.KAKAO_API) interceptor: KakaoInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Named(AppConst.KAKAO_PAY_API)
    fun provideKakaoInterceptor2(@Named("appContext") ctx: Context): KakaoInterceptor {
        return KakaoInterceptor(ctx.getString(R.string.kakao_admin_key))
    }

    @Provides
    @Named(AppConst.KAKAO_PAY_API)
    fun provideOkHttpClient2(loggingInterceptor: HttpLoggingInterceptor, @Named(AppConst.KAKAO_PAY_API) interceptor: KakaoInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Named(AppConst.INI_PAY_API)
    fun provideOkHttpClient3(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Named(AppConst.GITHUB_API)
    fun provideGithubInterceptor(@Named("appContext") ctx: Context): GithubInterceptor {
        return GithubInterceptor(ctx.getString(R.string.github_app_key))
    }

    @Provides
    @Named(AppConst.GITHUB_API)
    fun provideOkHttpClient4(loggingInterceptor: HttpLoggingInterceptor, @Named(AppConst.GITHUB_API) interceptor: GithubInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
