package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.StateWiseViewPagerAdapter

class StateWiseFragment : Fragment() {
lateinit var viewPagerAdapter: FragmentPagerAdapter
lateinit var viewPager: ViewPager
lateinit var tabs: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_state_wise, container, false)
        viewPager=view.findViewById(R.id.viewPager)
        viewPagerAdapter = StateWiseViewPagerAdapter(childFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabs = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        return view
    }
}