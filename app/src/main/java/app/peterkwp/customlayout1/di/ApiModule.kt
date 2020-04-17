package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.api.GithubApi
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
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named

@Module
class ApiModule {
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory
            = RxJava2CallAdapterFactory.createAsync()

    @Provides
    @Named("JSON_CONVERTER")
    fun provideConverterFactoryJson(): Converter.Factory
            = GsonConverterFactory.create()

    @Provides
    @Named("TEXT_CONVERTER")
    fun provideConverterFactoryText(): Converter.Factory
            = ScalarsConverterFactory.create()

    @Provides
    @Named(AppConst.KAKAO_API)
    fun provideApi1(
        @Named(AppConst.KAKAO_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        @Named("JSON_CONVERTER") converter: Converter.Factory): KakaoApi
            = Retrofit.Builder()
        .baseUrl(AppConst.KAKAO_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(KakaoApi::class.java)

    @Provides
    @Named(AppConst.KAKAO_PAY_API)
    fun provideApi2(
        @Named(AppConst.KAKAO_PAY_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        @Named("JSON_CONVERTER") converter: Converter.Factory): KakaoApi
            = Retrofit.Builder()
        .baseUrl(AppConst.KAKAO_PAY_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(KakaoApi::class.java)

    @Provides
    @Named(AppConst.INI_PAY_API)
    fun provideApi3(
        @Named(AppConst.INI_PAY_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        @Named("TEXT_CONVERTER") converter: Converter.Factory): InicisApi
            = Retrofit.Builder()
        .baseUrl(AppConst.INI_PAY_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(InicisApi::class.java)

    @Provides
    @Named(AppConst.GITHUB_API)
    fun provideApi4(
        @Named(AppConst.GITHUB_API) okHttpClient: OkHttpClient,
        callAdapter: CallAdapter.Factory,
        @Named("JSON_CONVERTER") converter: Converter.Factory): GithubApi
            = Retrofit.Builder()
        .baseUrl(AppConst.GITHUB_API_ADDR)
        .client(okHttpClient)
        .addCallAdapterFactory(callAdapter)
        .addConverterFactory(converter)
        .build()
        .create(GithubApi::class.java)
}