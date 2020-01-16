package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.MainActivity
import app.peterkwp.customlayout1.ui.filtersticker.FilterStickerFragment
import app.peterkwp.customlayout1.ui.kakaopay.KakaoPayFragment
import app.peterkwp.customlayout1.ui.lists.PagingListDefaultFragment
import app.peterkwp.customlayout1.ui.lists.PagingListFooterFragment
import app.peterkwp.customlayout1.ui.lists.PagingListFragment
import app.peterkwp.customlayout1.ui.lists.PagingListNestedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBinder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindKakaoPayFragment(): KakaoPayFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindFilterStickerFragment(): FilterStickerFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindpagingListFragment(): PagingListFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindpagingListDefaultFragment(): PagingListDefaultFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindpagingListFooterFragment(): PagingListFooterFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindpagingListNestedFragment(): PagingListNestedFragment
}