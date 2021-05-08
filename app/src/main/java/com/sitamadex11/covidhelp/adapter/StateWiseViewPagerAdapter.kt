package com.sitamadex11.covidhelp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sitamadex11.covidhelp.fragments.OtherStateFragment
import com.sitamadex11.covidhelp.fragments.WestBengalFragment

class StateWiseViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        lateinit var fragment:Fragment
        if(position==0) fragment=WestBengalFragment()
        else fragment=OtherStateFragment()
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        lateinit var title:String
        if(position==0) title="West Bengal"
        else title="All States"
        return title
    }

}