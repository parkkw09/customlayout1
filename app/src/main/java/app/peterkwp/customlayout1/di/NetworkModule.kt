package app.peterkwp.customlayout1.di

import android.content.Context
import android.util.Log
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.KakaoInterceptor
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
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, interceptor: KakaoInterceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

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
    fun provideKakaoInterceptor(@Named("appContext") ctx: Context): KakaoInterceptor {
        return KakaoInterceptor(ctx)
    }
}
