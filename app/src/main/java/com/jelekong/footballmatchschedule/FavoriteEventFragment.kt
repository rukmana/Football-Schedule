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
import com.jelekong.footballmatchschedule.R.color.colorAccent
import com.jelekong.footballmatchschedule.adapter.MatchAdapter
import com.jelekong.footballmatchschedule.database.Favorite
import com.jelekong.footballmatchschedule.database.database
import com.jelekong.footballmatchschedule.response.ListMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteEventFragment : Fragment(), AnkoComponent<Context> {

    private var listMatch: MutableList<ListMatch> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchAdapter(activity, 1, listMatch) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra("detailMatch", it)
            startActivity(intent)
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.setOnRefreshListener {
            listMatch.clear()
            showFavorite()
        }

    }

    override fun onResume() {
        super.onResume()
        listMatch.clear()
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
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                listEvent = recyclerView {
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
            val result = select(Favorite.TABLE_FAVORITE)
            val event = result.parseList(classParser<ListMatch>())
            listMatch.clear()
            listMatch.addAll(event)
            adapter.notifyDataSetChanged()
        }
    }
}