package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.PlayersResponse
import com.jelekong.footballmatchschedule.view.PlayersView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayersView,
                    private val apiRepository: ApiRespository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayers(playerId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(playerId)),
                        PlayersResponse::class.java
                )
            }

            view.showPlayersList(data.await().player)
            view.hideLoading()
        }
    }
}
