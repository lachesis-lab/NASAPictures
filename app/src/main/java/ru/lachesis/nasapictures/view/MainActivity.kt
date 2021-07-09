package ru.lachesis.nasapictures.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.util.SharedPrefHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeValue = SharedPrefHelper.getSharedPref()
        if (themeValue == 0) {
            setTheme(R.style.Theme_NASAPictures)
        }
        else {
            setTheme(R.style.Theme_NASAPicturesDark)
        }
//        it.recreate()

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance(null), "")
                .commit()
    }

}