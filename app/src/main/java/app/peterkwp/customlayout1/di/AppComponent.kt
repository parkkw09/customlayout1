package app.peterkwp.customlayout1.di

import android.app.Application
import app.peterkwp.customlayout1.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
     ApiModule::class,
     NetworkModule::class,
     AndroidSupportInjectionModule::class,
     AppBinder::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}