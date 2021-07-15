package ru.lachesis.nasapictures.view

import android.content.ContextWrapper
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.util.SharedPrefHelper

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val themeChip = it.findViewById<Chip>(R.id.theme_chip)
            val themeValue = SharedPrefHelper.getSharedPref()
            themeChip.isChecked = themeValue != 0
            if(themeChip.isChecked)
                themeChip.text = getString(R.string.theme_chip_text_dark)
            else
                themeChip.text = getString(R.string.theme_chip_text_light)

            themeChip.setOnClickListener { _ ->
                if (themeChip.isChecked) {
                    SharedPrefHelper.setSharedPref(1)
                } else {
                    SharedPrefHelper.setSharedPref(0)
                }
                it.recreate()
            }
        }
    }


/*
    private fun processData(themeIndex: Int) {

        if (themeIndex == 0)
            ContextThemeWrapper(context, R.style.Theme_NASAPictures)
        else
            ContextThemeWrapper(context, R.style.Theme_NASAPicturesDark)
    }
*/
}