package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jelekong.footballmatchschedule.OverviewFragment
import com.jelekong.footballmatchschedule.PlayersFragment

class TeamTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("OVERVIEW", "PLAYERS")

    override fun getItem(position: Int):
            Fragment {
        return when (position) {
            0 -> {
                OverviewFragment()
            }
            else -> {
                PlayersFragment()
            }
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}