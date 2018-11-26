package com.jelekong.footballmatchschedule

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.jelekong.footballmatchschedule.R.menu.detail_menu
import com.jelekong.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.jelekong.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.jelekong.footballmatchschedule.R.id.add_to_favorite
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.database.Favorite
import com.jelekong.footballmatchschedule.database.database
import com.jelekong.footballmatchschedule.presenter.TeamAwayPresenter
import com.jelekong.footballmatchschedule.presenter.TeamPresenter
import com.jelekong.footballmatchschedule.response.ListMatch
import com.jelekong.footballmatchschedule.response.Team
import com.jelekong.footballmatchschedule.util.DateConverter
import com.jelekong.footballmatchschedule.util.EspressoIdlingResource
import com.jelekong.footballmatchschedule.view.TeamAwayView
import com.jelekong.footballmatchschedule.view.TeamView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), TeamView, TeamAwayView {

    private lateinit var listMatch: ListMatch
    private lateinit var teamPresenter: TeamPresenter
    private lateinit var teamViewPresenter: TeamAwayPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        val actionbar = supportActionBar
        actionbar!!.title = resources.getString(R.string.detail_match)
        actionbar.setDisplayHomeAsUpEnabled(true)

        listMatch = intent.getParcelableExtra<ListMatch>("detailMatch")

        tv_date.text = listMatch.dateEvent
        tv_detail_home_team.text = listMatch.strHomeTeam
        tv_detail_away_team.text = listMatch.strAwayTeam
        tv_detail_score_home.text = listMatch.intHomeScore
        tv_detail_score_away.text = listMatch.intAwayScore

        tv_goals_home.text = listMatch.strHomeGoalDetails
        tv_goals_away.text = listMatch.strAwayGoalDetails
        tv_shoot_home.text = listMatch.intHomeShots
        tv_shoot_away.text = listMatch.intAwayShots

        tv_home_goals_keeper.text = listMatch.strHomeLineupGoalkeeper
        tv_away_goals_keeper.text = listMatch.strAwayLineupGoalkeeper
        tv_home_defend.text = listMatch.strHomeLineupDefense
        tv_away_defend.text = listMatch.strAwayLineupDefense
        tv_home_midfield.text = listMatch.strHomeLineupMidfield
        tv_away_midfield.text = listMatch.strAwayLineupMidfield
        tv_home_forward.text = listMatch.strHomeLineupForward
        tv_away_forward.text = listMatch.strAwayLineupForward
        tv_home_subs.text = listMatch.strHomeLineupSubstitutes
        tv_away_subs.text = listMatch.strAwayLineupSubstitutes

        try {
            val dateTime = DateConverter.formatGMT(listMatch.dateEvent, listMatch.strTime)
            val sdf = SimpleDateFormat("HH:mm").format(dateTime)
            tv_time.text = sdf
        } catch (e: Exception) {
            e.printStackTrace()
        }

        progressBar = findViewById(R.id.pb_away_image)

        val request = ApiRespository()
        val gson = Gson()
        teamPresenter = TeamPresenter(this, request, gson)
        EspressoIdlingResource.increment()
        teamPresenter.getTeams(listMatch.idHomeTeam)

        teamViewPresenter = TeamAwayPresenter(this, request, gson)

        teamViewPresenter.getTeams(listMatch.idAwayTeam)
        favoriteState()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showLoading() {
        pb_home_image.visibility = View.VISIBLE
        iv_detail_home_team.visibility = View.GONE
    }

    override fun hideLoading() {
        pb_home_image.visibility = View.GONE
        iv_detail_home_team.visibility = View.VISIBLE
    }

    override fun showTeamList(data: List<Team>) {
        pb_home_image.visibility = View.GONE
        iv_detail_home_team.visibility = View.VISIBLE

        Picasso.get().load(data[0].strTeamBadge).into(iv_detail_home_team)
    }

    override fun showLoadingAway() {
        pb_away_image.visibility = View.VISIBLE
        iv_detail_away_team.visibility = View.GONE
    }

    override fun hideLoadingAway() {
        pb_away_image.visibility = View.GONE
        iv_detail_away_team.visibility = View.VISIBLE
    }

    override fun showTeamLisAway(data: List<Team>) {
        pb_away_image.visibility = View.GONE
        iv_detail_away_team.visibility = View.VISIBLE

        Picasso.get().load(data[0].strTeamBadge).into(iv_detail_away_team)
        EspressoIdlingResource.decrement()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite)
                    removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to listMatch.idEvent,
                        Favorite.EVENT_DATE to listMatch.dateEvent,
                        Favorite.EVENT_HOME_NAME to listMatch.strHomeTeam,
                        Favorite.EVENT_AWAY_NAME to listMatch.strAwayTeam,
                        Favorite.EVENT_HOME_SCORE to listMatch.intHomeScore,
                        Favorite.EVENT_AWAY_SCORE to listMatch.intAwayScore,
                        Favorite.EVENT_HOME_GOALS to listMatch.strHomeGoalDetails,
                        Favorite.EVENT_AWAY_GOALS to listMatch.strAwayGoalDetails,
                        Favorite.EVENT_HOME_SHOTS to listMatch.intHomeShots,
                        Favorite.EVENT_AWAY_SHOTS to listMatch.intAwayShots,
                        Favorite.EVENT_HOME_GOALS_KEEPER to listMatch.strHomeLineupGoalkeeper,
                        Favorite.EVENT_AWAY_GOALS_KEEPER to listMatch.strAwayLineupGoalkeeper,
                        Favorite.EVENT_HOME_DEFENS to listMatch.strHomeLineupDefense,
                        Favorite.EVENT_AWAY_DEFENS to listMatch.strAwayLineupDefense,
                        Favorite.EVENT_HOME_MIDFIELD to listMatch.strHomeLineupMidfield,
                        Favorite.EVENT_AWAY_MIDFIELD to listMatch.strAwayLineupMidfield,
                        Favorite.EVENT_HOME_GOALS_FORWARD to listMatch.strHomeLineupForward,
                        Favorite.EVENT_AWAY_GOALS_FORWARD to listMatch.strAwayLineupForward,
                        Favorite.EVENT_HOME_GOALS_SUBSTITUTES to listMatch.strHomeLineupSubstitutes,
                        Favorite.EVENT_AWAY_GOALS_SUBSTITUTES to listMatch.strAwayLineupSubstitutes,
                        Favorite.EVENT_HOME_TEAM_ID to listMatch.idHomeTeam,
                        Favorite.EVENT_AWAY_TEAM_ID to listMatch.idAwayTeam,
                        Favorite.EVENT_TIME to listMatch.strTime,
                        Favorite.EVENT_SPORT to listMatch.strSport)
            }
            snackbar(progressBar, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE,
                        "(" + Favorite.EVENT_ID + "={event_id})",
                        "event_id" to listMatch.idEvent
                )
            }
            snackbar(progressBar, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to listMatch.idEvent)
            val favorite = result.parseList(classParser<ListMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }

    }

}