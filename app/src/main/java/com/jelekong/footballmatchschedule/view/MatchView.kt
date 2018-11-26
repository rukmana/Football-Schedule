package com.jelekong.footballmatchschedule.view

import com.jelekong.footballmatchschedule.response.ListMatch

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<ListMatch>)
//    fun showLeagueList(data: LeagueResponse)
}