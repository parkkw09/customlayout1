package app.peterkwp.customlayout1.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AppModule {

    @Provides
    @Named("appContext")
    fun provideContext(application: Application): Context = application.applicationContext
}