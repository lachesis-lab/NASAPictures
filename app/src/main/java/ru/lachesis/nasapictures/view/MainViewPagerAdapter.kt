package ru.lachesis.nasapictures.view

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    var items: List<Fragment> = listOf<Fragment>(

        PictureFragment.newInstance(0),
        PictureFragment.newInstance(1),
        PictureFragment.newInstance(2))

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        Log.d("AdapterPosition", position.toString())
        //return  PictureFragment.newInstance(position)//
        return items[position]
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
//        items.add(position,fragment)
        Log.d("InstantiatePosition", position.toString())
        return fragment
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        items.drop(position)
        super.destroyItem(container, position, `object`)
    }


    fun getRegisteredItem(container: ViewGroup, position: Int): Fragment {
        Log.d("RegisteredPosition", position.toString())
        //return instantiateItem(container,position) as PictureFragment
        return items.get(position)
    }

//    override fun getItemPosition(`object`: Any): Int {
//        return POSITION_NONE
//        val position = super.getItemPosition(`object`)
//        Log.d("ItemPosition",position.toString())
//        return position
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