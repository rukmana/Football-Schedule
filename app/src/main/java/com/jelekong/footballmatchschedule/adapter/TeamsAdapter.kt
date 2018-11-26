package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.jelekong.footballmatchschedule.R
import com.jelekong.footballmatchschedule.response.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_teams.view.*

class TeamsAdapter(private val context: FragmentActivity?,
                   private val teams: List<Team>,
                   private val listTeams:(Team) -> Unit)
    : RecyclerView.Adapter<TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder (LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(teams[position], listTeams)
    }
}

class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(teams: Team,detailMatch: (Team) -> Unit) {

        itemView.tv_team_name.text = teams.strTeam
        Picasso.get().load(teams.strTeamBadge).into(itemView.iv_team_logo)

        itemView.setOnClickListener { detailMatch(teams) }
    }
}