package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.MainActivity
import app.peterkwp.customlayout1.ui.kakaopay.KakaoPayFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBinder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindKakaoPayFragment(): KakaoPayFragment
}