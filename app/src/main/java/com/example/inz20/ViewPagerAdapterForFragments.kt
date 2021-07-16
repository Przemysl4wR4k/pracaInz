package com.example.inz20

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapterForFragments (fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val fragments: ArrayList<Fragment>
    private val titles: ArrayList<String>

    init{
        fragments = ArrayList<Fragment>()
        titles = ArrayList<String>()
    }
    override fun getCount() = fragments.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}