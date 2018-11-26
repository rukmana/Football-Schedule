package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jelekong.footballmatchschedule.FavoriteEventFragment
import com.jelekong.footballmatchschedule.MatchesFragment
import com.jelekong.footballmatchschedule.NextMatchFragment

class MainActivityAdapter(fragmentManager: FragmentManager,
                          private val matchesFragment: MatchesFragment,
                          private val nextMatchFragment: NextMatchFragment,
                          private val favoriteEventFragment: FavoriteEventFragment) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            matchesFragment
        } else if (position == 1){
            nextMatchFragment
        } else {
            favoriteEventFragment
        }
    }

    override fun getCount(): Int = 3
}