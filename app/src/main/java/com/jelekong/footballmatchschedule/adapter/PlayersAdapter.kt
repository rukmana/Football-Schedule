package com.jelekong.footballmatchschedule.adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jelekong.footballmatchschedule.R
import com.jelekong.footballmatchschedule.response.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player.view.*

class PlayersAdapter(private val context: FragmentActivity?,
                   private val player: List<Player>,
                   private val listPlayer:(Player) -> Unit)
    : RecyclerView.Adapter<PlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder (LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))
    }

    override fun getItemCount(): Int = player.size

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.bindItem(player[position], listPlayer)
    }
}

class PlayersViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(player: Player, detailMatch: (Player) -> Unit) {

        itemView.tv_player_name.text = player.strPlayer
        itemView.tv_position.text = player.strPosition
        Picasso.get().load(player.strCutout).into(itemView.iv_player)

        itemView.setOnClickListener { detailMatch(player) }
    }
}