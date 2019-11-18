package app.peterkwp.customlayout1.di

import app.peterkwp.customlayout1.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindActivity(): MainActivity
}