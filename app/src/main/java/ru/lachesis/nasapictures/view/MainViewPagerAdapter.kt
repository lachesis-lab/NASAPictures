package ru.lachesis.nasapictures.view

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    var items: List<Fragment> = listOf(
        PictureFragment.newInstance(0),
        PictureFragment.newInstance(1),
        PictureFragment.newInstance(2))

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        Log.d("AdapterPosition",position.toString())
        return  items[position]
    }

//    override fun getItemPosition(`object`: Any): Int {
//        return POSITION_NONE
//        //return super.getItemPosition(`object`)
//    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Позавчера"
            1 -> return "Вчера"
            2 -> return "Сегодня"
        }
        return ""
    }

}