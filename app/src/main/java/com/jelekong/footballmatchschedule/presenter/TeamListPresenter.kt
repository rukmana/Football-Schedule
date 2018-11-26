package com.jelekong.footballmatchschedule.presenter

import com.jelekong.footballmatchschedule.view.TeamListView
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.TeamsResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class TeamListPresenter(private val view: TeamListView,
                        private val apiRepository: ApiRespository,
                        private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getTeamList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamsAll(league)),
                        TeamsResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    fun getSearchTeam(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getAllTeam(query)),
                        TeamsResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}