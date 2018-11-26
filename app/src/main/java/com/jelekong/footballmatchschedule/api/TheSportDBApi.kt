package com.jelekong.footballmatchschedule.api

import android.net.Uri
import com.jelekong.footballmatchschedule.BuildConfig

object TheSportDBApi {

    private const val url = "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}"

    fun getLastMatch(leagueId: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=${leagueId}"
    }

    fun getNextMatch(leagueId: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=${leagueId}"
    }

    fun getTeams(teamId: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=${teamId}"
    }

    fun getPlayer(playerId: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookup_all_players.php?id=${playerId}"
    }

    fun getTeamsAll(league: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
            "/search_all_teams.php?l=${league}"
    }

    fun getAllTeam(query: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchteams.php?t=" + query
    }

    fun getAllEvent(query: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchevents.php?e=" + query
    }
}