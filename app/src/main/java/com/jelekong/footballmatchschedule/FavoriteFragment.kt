package com.jelekong.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import com.jelekong.footballmatchschedule.adapter.FavoriteTabAdapter

class FavoriteFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite, container, false)

        setHasOptionsMenu(true)
        viewPager = view.findViewById<ViewPager>(R.id.viewpager_main)
        tabLayout = view.findViewById<TabLayout>(R.id.tabs_main)

        val fragmentAdapter = FavoriteTabAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}