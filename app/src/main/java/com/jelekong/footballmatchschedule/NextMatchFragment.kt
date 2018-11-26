package com.jelekong.footballmatchschedule

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.adapter.MatchAdapter
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.presenter.MatchPresenter
import com.jelekong.footballmatchschedule.response.ListMatch
import com.jelekong.footballmatchschedule.util.EspressoIdlingResource
import com.jelekong.footballmatchschedule.view.MatchView
import org.jetbrains.anko.support.v4.ctx

class NextMatchFragment : Fragment(), MatchView {

    private var matchs: MutableList<ListMatch> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var matchPresenter: MatchPresenter
    private lateinit var spinner: Spinner
    var leagueId = 4328
    //    private lateinit var league: Leagues
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_next_match, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.rv_next)
        spinner = view.findViewById(R.id.spinner_next)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MatchAdapter(activity, 0, matchs) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra("detailMatch", it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val request = ApiRespository()
        val gson = Gson()
        matchPresenter = MatchPresenter(this, request, gson)
        EspressoIdlingResource.increment()

        showSpinnerData()

        return view
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun showTeamList(data: List<ListMatch>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
        EspressoIdlingResource.decrement()
    }

    fun showSpinnerData() {

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                matchs.clear()
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                leagueName = spinner.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> matchPresenter.getNextList("4328")
                    "English League Championship" -> matchPresenter.getNextList("4329")
                    "Scottish Premier League" -> matchPresenter.getNextList("4330")
                    "German Bundesliga" -> matchPresenter.getNextList("4331")
                    "Italian Serie A" -> matchPresenter.getNextList("4332")
                    "French Ligue 1" -> matchPresenter.getNextList("4334")
                    "Spanish La Liga" -> matchPresenter.getNextList("4335")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}