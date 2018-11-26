package com.jelekong.footballmatchschedule.view

import com.jelekong.footballmatchschedule.response.Team

interface TeamListView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}