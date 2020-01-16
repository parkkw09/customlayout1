package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.api.GithubApi
import app.peterkwp.customlayout1.api.InicisApi
import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.feature.AppConst
import app.peterkwp.customlayout1.ui.filtersticker.FilterStickerViewModelFactory
import app.peterkwp.customlayout1.ui.kakaopay.KakaoPayViewModelFactory
import app.peterkwp.customlayout1.ui.lists.PagingListViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainModule {

    @Provides
    fun provideViewModelFactory(@Named(AppConst.KAKAO_PAY_API) api: KakaoApi,
                                @Named(AppConst.INI_PAY_API) api2: InicisApi): KakaoPayViewModelFactory
            = KakaoPayViewModelFactory(api, api2)

    @Provides
    fun provideViewModelFactory2(@Named(AppConst.KAKAO_API) api: KakaoApi,
                                 @Named(AppConst.GITHUB_API) api2: GithubApi): FilterStickerViewModelFactory
            = FilterStickerViewModelFactory(api, api2)

    @Provides
    fun provideViewModelFactory3(@Named(AppConst.KAKAO_API) api: KakaoApi): PagingListViewModelFactory
            = PagingListViewModelFactory(api)
}