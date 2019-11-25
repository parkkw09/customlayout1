package app.peterkwp.customlayout1.di

import android.content.Context
import android.util.Log
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.api.KakaoInterceptor
import app.peterkwp.customlayout1.feature.AppConst
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
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
    @Singleton
    @Named(AppConst.KAKAO_API)
    fun provideKakaoInterceptor1(@Named("appContext") ctx: Context): KakaoInterceptor {
        return KakaoInterceptor(ctx.getString(R.string.kakao_rest_key))
    }

    @Provides
    @Singleton
    @Named(AppConst.KAKAO_API)
    fun provideOkHttpClient1(loggingInterceptor: HttpLoggingInterceptor, @Named(AppConst.KAKAO_API) interceptor: KakaoInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    @Named(AppConst.KAKAO_PAY_API)
    fun provideKakaoInterceptor2(@Named("appContext") ctx: Context): KakaoInterceptor {
        return KakaoInterceptor(ctx.getString(R.string.kakao_admin_key))
    }

    @Provides
    @Singleton
    @Named(AppConst.KAKAO_PAY_API)
    fun provideOkHttpClient2(loggingInterceptor: HttpLoggingInterceptor, @Named(AppConst.KAKAO_PAY_API) interceptor: KakaoInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    @Named(AppConst.INI_PAY_API)
    fun provideOkHttpClient3(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
