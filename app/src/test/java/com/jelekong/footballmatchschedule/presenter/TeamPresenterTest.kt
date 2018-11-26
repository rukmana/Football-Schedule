package com.jelekong.footballmatchschedule.presenter

import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.api.TheSportDBApi
import com.jelekong.footballmatchschedule.response.Team
import com.jelekong.footballmatchschedule.response.TeamsResponse
import com.jelekong.footballmatchschedule.view.TeamView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    private lateinit var presenterTeam: TeamPresenter

    @Mock
    private
    lateinit var viewTeam: TeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRespository

    @Before
    fun setUpTeam() {
        MockitoAnnotations.initMocks(this)
        presenterTeam = TeamPresenter(viewTeam, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeams() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamsResponse(teams)
        val id = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamsAll(id)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenterTeam.getTeams(id)

        verify(viewTeam).showLoading()
        verify(viewTeam).showTeamList(teams)
        verify(viewTeam).hideLoading()
    }
}