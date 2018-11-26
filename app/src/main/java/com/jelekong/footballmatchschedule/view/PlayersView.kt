package com.jelekong.footballmatchschedule.view

import com.jelekong.footballmatchschedule.response.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayersList(data: List<Player>)
}