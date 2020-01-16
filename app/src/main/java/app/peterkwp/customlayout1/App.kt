package app.peterkwp.customlayout1

import app.peterkwp.customlayout1.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    companion object {
        const val TAG = "custom1"
        const val THRESHOLD = 1
        const val CONTINUE = 2
        const val COMPLETE = 3
        const val END = 4
        const val ITEM = 4
        const val FOOTER = 5
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}