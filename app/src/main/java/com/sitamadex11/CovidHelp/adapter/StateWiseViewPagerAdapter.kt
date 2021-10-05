package com.sitamadex11.CovidHelp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sitamadex11.CovidHelp.fragments.OtherStateFragment
import com.sitamadex11.CovidHelp.fragments.WestBengalFragment

class StateWiseViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        lateinit var fragment: Fragment
        if (position == 0) fragment = WestBengalFragment()
        else fragment = OtherStateFragment()
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        lateinit var title: String
        if (position == 0) title = "West Bengal"
        else title = "Other States"
        return title
    }

}