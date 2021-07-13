package ru.lachesis.nasapictures.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return  PictureFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Позавчера"
            1 -> return "Вчера"
            2 -> return "Сегодня"
        }
        return ""
    }

}