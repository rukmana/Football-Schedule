package com.jelekong.footballmatchschedule

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import com.jelekong.footballmatchschedule.R.menu.search
import com.jelekong.footballmatchschedule.adapter.MatchesTabAdapter

class MatchesFragment : Fragment() {

    private lateinit var matchesTabAdapter: MatchesTabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var menuItem: Menu? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_matches, container, false)

        setHasOptionsMenu(true)
        viewPager = view.findViewById<ViewPager>(R.id.viewpager_mathes)
        tabLayout = view.findViewById<TabLayout>(R.id.tabs_matches)

        val fragmentAdapter = MatchesTabAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val intent = Intent(context, SearchMatchActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}