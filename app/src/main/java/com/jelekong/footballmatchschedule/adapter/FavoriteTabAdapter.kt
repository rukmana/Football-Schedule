package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jelekong.footballmatchschedule.FavoriteEventFragment
import com.jelekong.footballmatchschedule.FavoriteTeamFragment

class FavoriteTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("MATCHES", "TEAMS")

    override fun getItem(position: Int):
            Fragment {
        return when (position) {
            0 -> {
                FavoriteEventFragment()
            }
            else -> {
                FavoriteTeamFragment()
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