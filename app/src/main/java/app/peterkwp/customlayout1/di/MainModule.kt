package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.feature.AppConst
import app.peterkwp.customlayout1.ui.kakaopay.KakaoPayViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainModule {

    @Provides
    fun provideViewModelFactory(@Named(AppConst.KAKAO_PAY_API) api: KakaoApi): KakaoPayViewModelFactory
            = KakaoPayViewModelFactory(api)
}