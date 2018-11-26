package com.jelekong.footballmatchschedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jelekong.footballmatchschedule.adapter.TeamsAdapter
import com.jelekong.footballmatchschedule.database.FavoriteTeam
import com.jelekong.footballmatchschedule.database.database
import com.jelekong.footballmatchschedule.response.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment(), AnkoComponent<Context> {

    private var teamData: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamsAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamsAdapter(activity, teamData) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("detailTeam", it)
            startActivity(intent)
        }

        listTeam.adapter = adapter
        showFavorite()
        swipeRefresh.setOnRefreshListener {
            teamData.clear()
            showFavorite()
        }

    }

    override fun onResume() {
        super.onResume()
        teamData.clear()
        showFavorite()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                listTeam = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_favorite
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val team = result.parseList(classParser<Team>())
            teamData.clear()
            teamData.addAll(team)
            adapter.notifyDataSetChanged()
        }
    }
}