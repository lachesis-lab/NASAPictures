package ru.lachesis.nasapictures.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(PictureFragment.BUNDLE_EXTRA,position-1)
        return PictureFragment.newInstance(bundle)
    }
}