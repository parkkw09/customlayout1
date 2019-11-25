package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.api.InicisApi
import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.feature.AppConst
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory
            = RxJava2CallAdapterFactory.createAsync()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory
            = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named(AppConst.KAKAO_API)
    fun provideApi1(
        @Named(AppConst.KAKAO_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        converter: Converter.Factory): KakaoApi
            = Retrofit.Builder()
        .baseUrl(AppConst.KAKAO_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(KakaoApi::class.java)

    @Provides
    @Singleton
    @Named(AppConst.KAKAO_PAY_API)
    fun provideApi2(
        @Named(AppConst.KAKAO_PAY_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        converter: Converter.Factory): KakaoApi
            = Retrofit.Builder()
        .baseUrl(AppConst.KAKAO_PAY_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(KakaoApi::class.java)

    @Provides
    @Singleton
    @Named(AppConst.INI_PAY_API)
    fun provideApi3(
        @Named(AppConst.INI_PAY_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        converter: Converter.Factory): InicisApi
            = Retrofit.Builder()
        .baseUrl(AppConst.INI_PAY_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(InicisApi::class.java)
}