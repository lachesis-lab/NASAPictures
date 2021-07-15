package ru.lachesis.nasapictures.app

import android.app.Application
import android.content.Context

class App: Application() {
    companion object{
        private var appInstance: App? = null
        val appContext: Context by lazy { appInstance!!.applicationContext}
        const val PREFERENCES:String = "sharedprefs.xml"
        const val PREF_NAME:String = "theme"
    }

    override fun onCreate() {
        super.onCreate()
        appInstance=this
    }
}