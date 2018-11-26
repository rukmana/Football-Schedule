package com.jelekong.footballmatchschedule.view

import com.jelekong.footballmatchschedule.response.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}