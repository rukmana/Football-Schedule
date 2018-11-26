package com.jelekong.footballmatchschedule.presenter

import android.os.SystemClock
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.ListMatch
import com.jelekong.footballmatchschedule.response.ListMatchResponse
import com.jelekong.footballmatchschedule.response.allMatchResponse
import com.jelekong.footballmatchschedule.view.MatchView
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Test
import org.mockito.Mockito.*

class MatchPresenterTest {

    private lateinit var presenter: MatchPresenter

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRespository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamListTest() {
        SystemClock.sleep(15000)
        val matchs: MutableList<ListMatch> = mutableListOf()
        val response = ListMatchResponse(matchs)
        val leagueId = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(leagueId)),
                ListMatchResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(leagueId)

        verify(view).showLoading()
        verify(view).showTeamList(matchs)
        verify(view).hideLoading()
    }

    @Test
    fun getNextListTest() {
        SystemClock.sleep(5000)
        val matchs: MutableList<ListMatch> = mutableListOf()
        val response = ListMatchResponse(matchs)
        val leagueId = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueId)),
                ListMatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextList(leagueId)

        verify(view).showLoading()
        verify(view).showTeamList(matchs)
        verify(view).hideLoading()
    }

    @Test
    fun getAllEventTest() {
        SystemClock.sleep(5000)
        val matchs: MutableList<ListMatch> = mutableListOf()
        val response = allMatchResponse(matchs)
        val query = "chelsea"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAllEvent(query)),
                allMatchResponse::class.java
        )).thenReturn(response)

        presenter.getAllEvent(query)

        verify(view).showLoading()
        verify(view).showTeamList(matchs)
        verify(view).hideLoading()
    }
}