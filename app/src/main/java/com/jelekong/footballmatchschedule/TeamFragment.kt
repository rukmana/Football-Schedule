package com.jelekong.footballmatchschedule

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.adapter.TeamsAdapter
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.presenter.TeamListPresenter
import com.jelekong.footballmatchschedule.response.Team
import com.jelekong.footballmatchschedule.util.EspressoIdlingResource
import com.jelekong.footballmatchschedule.view.TeamListView
import org.jetbrains.anko.support.v4.ctx

class TeamFragment : Fragment(), TeamListView {

    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    private lateinit var teamPresenter: TeamListPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: TeamsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_team, container, false)

        setHasOptionsMenu(true)
        spinner = view.findViewById(R.id.spinner)
        recyclerView = view.findViewById(R.id.rv_teams)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = TeamsAdapter(activity, teams) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra("detailTeam", it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        val request = ApiRespository()
        val gson = Gson()
        teamPresenter = TeamListPresenter(this, request, gson)

        showSpinnerData()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val intent = Intent(context, SearchTeamActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun showSpinnerData() {

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
//                teamPresenter.getTeamList(leagueName)
                when (leagueName) {
                    "English Premier League" -> teamPresenter.getTeamList("English%20Premier%20League")
                    "English League Championship" -> teamPresenter.getTeamList("English%20League%20Championship")
                    "Scottish Premier League" -> teamPresenter.getTeamList("Scottish%20Premier%20League")
                    "German Bundesliga" -> teamPresenter.getTeamList("German%20Bundesliga")
                    "Italian Serie A" -> teamPresenter.getTeamList("Italian%20Serie A")
                    "French Ligue 1" -> teamPresenter.getTeamList("French%20Ligue%201")
                    "Spanish La Liga" -> teamPresenter.getTeamList("Spanish%20La%20Liga")
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun showTeamList(data: List<Team>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
        EspressoIdlingResource.decrement()
    }

}