package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.Team
import com.jelekong.footballmatchschedule.response.TeamsResponse
import com.jelekong.footballmatchschedule.view.TeamListView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamListPresenterTest {

    private lateinit var presenter: TeamListPresenter

    @Mock
    private
    lateinit var viewTeamList: TeamListView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRespository

    @Before
    fun setUpTeam() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamListPresenter(viewTeamList, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamsResponse(teams)
        val leagues = "English%20Premier%20League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamsAll(leagues)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(leagues)

        verify(viewTeamList).showLoading()
        verify(viewTeamList).showTeamList(teams)
        verify(viewTeamList).hideLoading()
    }

    @Test
    fun getSearchTeam() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamsResponse(teams)
        val query = "arsenal"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAllTeam(query)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenter.getSearchTeam(query)

        verify(viewTeamList).showLoading()
        verify(viewTeamList).showTeamList(teams)
        verify(viewTeamList).hideLoading()
    }

}