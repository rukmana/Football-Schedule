package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.ListMatchResponse
import com.jelekong.footballmatchschedule.response.allMatchResponse
import com.jelekong.footballmatchschedule.view.MatchView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRespository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLastMatch(leagueId)),
                        ListMatchResponse::class.java
                )
            }
            view.showTeamList(data.await().events)
            view.hideLoading()
        }
    }

    fun getNextList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(leagueId)),
                        ListMatchResponse::class.java
                )
            }
            view.showTeamList(data.await().events)
            view.hideLoading()
        }
    }

    fun getAllEvent(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getAllEvent(query)),
                        allMatchResponse::class.java
                )
            }
            view.showTeamList(data.await().event)
            view.hideLoading()
        }
    }

}