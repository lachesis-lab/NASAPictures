package ru.lachesis.nasapictures.util

import android.content.Context
import ru.lachesis.nasapictures.app.App


class SharedPrefHelper {

    companion object {
        private val sharedPref =
            App.appContext.getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)

        fun getSharedPref(): Int {
            return sharedPref.getInt(App.PREF_NAME, 0)

        }

        fun setSharedPref(value: Int) {
            sharedPref.edit().putInt(App.PREF_NAME, value).apply()
        }
    }
}