package com.jelekong.footballmatchschedule

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.adapter.PlayersAdapter
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.presenter.PlayerPresenter
import com.jelekong.footballmatchschedule.response.Player
import com.jelekong.footballmatchschedule.util.EspressoIdlingResource
import com.jelekong.footballmatchschedule.view.PlayersView


class PlayersFragment : Fragment(), PlayersView {

    private var players: MutableList<Player> = mutableListOf()

    private lateinit var playerPresenter: PlayerPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PlayersAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var idTeam: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_players, container, false)

        recyclerView = view.findViewById(R.id.rv_player)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlayersAdapter(activity, players) {
            val intent = Intent(context, DetailPlayerActivity::class.java)
            intent.putExtra("detailPlayer", it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        val request = ApiRespository()
        val gson = Gson()
        playerPresenter = PlayerPresenter(this, request, gson)

        idTeam = (activity as DetailTeamActivity).setIdTeam()
        playerPresenter.getPlayers(idTeam)

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

    override fun showPlayersList(data: List<Player>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
        EspressoIdlingResource.decrement()
    }
}