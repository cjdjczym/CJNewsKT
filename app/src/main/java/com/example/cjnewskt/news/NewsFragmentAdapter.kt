package com.example.cjnewskt.news

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class NewsFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fList = mutableListOf<Fragment>()

    fun addFragment(f: Fragment) = fList.add(f)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment = fList[position]

    override fun getCount(): Int = fList.size
}