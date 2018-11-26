package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jelekong.footballmatchschedule.LastMatchFragment
import com.jelekong.footballmatchschedule.NextMatchFragment

class MatchesTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("NEXT MATCH", "LAST MATCH")

    override fun getItem(position: Int):
            Fragment {
        return when (position) {
            0 -> {
                NextMatchFragment()
            }
            else -> {
                LastMatchFragment()
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