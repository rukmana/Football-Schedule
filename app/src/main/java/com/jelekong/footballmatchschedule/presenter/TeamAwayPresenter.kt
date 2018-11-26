package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.TeamsResponse
import com.jelekong.footballmatchschedule.view.TeamAwayView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamAwayPresenter(private val view: TeamAwayView,
                        private val apiRepository: ApiRespository,
                        private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeams(teamId: String?) {
        view.showLoadingAway()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(teamId)),
                        TeamsResponse::class.java
                )
            }

            view.showTeamLisAway(data.await().teams)
            view.hideLoadingAway()
        }
    }
}