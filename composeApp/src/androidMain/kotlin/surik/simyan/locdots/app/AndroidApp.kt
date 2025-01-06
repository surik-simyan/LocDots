package surik.simyan.locdots.app

import android.app.Application
import surik.simyan.locdots.app.di.KoinInitializer

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}