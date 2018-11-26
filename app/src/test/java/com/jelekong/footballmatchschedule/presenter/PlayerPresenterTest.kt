package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.Player
import com.jelekong.footballmatchschedule.response.PlayersResponse
import com.jelekong.footballmatchschedule.view.PlayersView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    private lateinit var presenter: PlayerPresenter

    @Mock
    private
    lateinit var view: PlayersView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRespository

    @Before
    fun setUpTeam() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeams() {
        val player: MutableList<Player> = mutableListOf()
        val response = PlayersResponse(player)
        val id = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayer(id)),
                PlayersResponse::class.java
        )).thenReturn(response)

        presenter.getPlayers(id)

        verify(view).showLoading()
        verify(view).showPlayersList(player)
        verify(view).hideLoading()
    }
}