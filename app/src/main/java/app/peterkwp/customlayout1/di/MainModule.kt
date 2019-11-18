package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.ui.kakaopay.KakaoPayViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideViewModelFactory(api: KakaoApi): KakaoPayViewModelFactory
            = KakaoPayViewModelFactory(api)
}