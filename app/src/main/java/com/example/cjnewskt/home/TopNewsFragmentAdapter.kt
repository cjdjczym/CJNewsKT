package com.example.cjnewskt.home

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TopNewsFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fList = mutableListOf<Fragment>()

    fun addFragment(f: Fragment) = fList.add(f)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment = fList[position % fList.size]

    override fun getCount(): Int = fList.size * 30
}