package com.jelekong.footballmatchschedule.adapter

import android.content.Intent
import android.provider.CalendarContract
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jelekong.footballmatchschedule.R
import com.jelekong.footballmatchschedule.response.ListMatch
import com.jelekong.footballmatchschedule.util.DateConverter.Companion.formatGMT
import com.jelekong.footballmatchschedule.util.DateConverter.Companion.formatDate
import kotlinx.android.synthetic.main.item_match.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MatchAdapter(private val context: FragmentActivity?,
                   private val lastData: Int,
                   private val teams: List<ListMatch>,
                   private val detailMatch: (ListMatch) -> Unit)
    : RecyclerView.Adapter<ListMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMatchViewHolder {
        return ListMatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ListMatchViewHolder, position: Int) {
        holder.bindItem(teams[position], lastData, detailMatch)
    }
}

class ListMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(teams: ListMatch, lastData: Int, detailMatch: (ListMatch) -> Unit) {
        itemView.date_event.text = teams.dateEvent
        itemView.tv_home_team.text = teams.strHomeTeam
        itemView.tv_away_team.text = teams.strAwayTeam
        itemView.tv_score_home.text = teams.intHomeScore
        itemView.tv_score_away.text = teams.intAwayScore

        if (lastData == 1) {
            itemView.ivAlert.visibility = View.GONE
        }

        try {
            val dateTime = formatGMT(teams.dateEvent, teams.strTime)
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault()).format(dateTime)
            itemView.time_event.text = sdf
        } catch (e: Exception) {
            e.printStackTrace()
        }
        itemView.ivAlert.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = "vnd.android.cursor.item/event"

            val dateTime = teams.dateEvent + " " + teams.strTime
            val startTime = dateTime.formatDate()
            val endTime = startTime + TimeUnit.MINUTES.toMillis(90)

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
            intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, TimeUnit.MINUTES.toMillis(90))

            intent.putExtra(CalendarContract.Events.TITLE,
                    "${teams.strHomeTeam} vs ${teams.strAwayTeam}")

            intent.putExtra(CalendarContract.Events.DESCRIPTION,
                    "${teams.strHomeTeam} vs ${teams.strAwayTeam}")

            itemView.context.startActivity(intent)
        }

        itemView.setOnClickListener { detailMatch(teams) }
    }
}