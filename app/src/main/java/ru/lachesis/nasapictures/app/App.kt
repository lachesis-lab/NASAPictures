package ru.lachesis.nasapictures.app

import android.app.Application
import android.content.Context

class App: Application() {
    companion object{
        private var appInstance: App? = null
        val appContext: Context by lazy { appInstance!!.applicationContext}
    }

    override fun onCreate() {
        super.onCreate()
        appInstance=this
    }
}