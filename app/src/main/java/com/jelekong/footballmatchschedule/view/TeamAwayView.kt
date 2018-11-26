package com.jelekong.footballmatchschedule.view

import com.jelekong.footballmatchschedule.response.Team

interface TeamAwayView {
    fun showLoadingAway()
    fun hideLoadingAway()
    fun showTeamLisAway(data: List<Team>)
}