package com.jelekong.footballmatchschedule

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.jelekong.footballmatchschedule.adapter.TeamTabAdapter
import com.jelekong.footballmatchschedule.database.FavoriteTeam
import com.jelekong.footballmatchschedule.database.database
import com.jelekong.footballmatchschedule.response.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity() {

    private lateinit var teamData: Team
    lateinit var desc: String
    lateinit var idPlayer: String
    private lateinit var progressBar: ProgressBar
    private var isFavorite: Boolean = false
    private lateinit var ivAddFavorite: ImageView
    private lateinit var ivAddedFavorite: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val fragmentAdapter = TeamTabAdapter(supportFragmentManager)
        viewpager_detail_team.adapter = fragmentAdapter
        detail_tab_team.setupWithViewPager(viewpager_detail_team)

        teamData = intent.getParcelableExtra<Team>("detailTeam")
        ivAddFavorite = findViewById(R.id.iv_add_to_favorite_team)
        ivAddedFavorite = findViewById(R.id.iv_added_to_favorite_team)
        progressBar = findViewById(R.id.pb_team)

        ivAddFavorite.setOnClickListener {
            ivAddFavorite.visibility = View.GONE
            ivAddedFavorite.visibility = View.VISIBLE
            addToFavorite()
        }

        ivAddedFavorite.setOnClickListener {
            ivAddFavorite.visibility = View.VISIBLE
            ivAddedFavorite.visibility = View.GONE
            removeFromFavorite()
        }

        tv_team_name.text = teamData.strTeam
        tv_year.text = teamData.intFormedYear
        tv_stadium.text = teamData.strStadium
        desc = teamData.strDescriptionEN.toString()
        idPlayer = teamData.idTeam.toString()

        Picasso.get().load(teamData.strTeamBadge).into(iv_logo_team)

        iv_back.setOnClickListener { onBackPressed() }

        favoriteState()
        setFavorite()
    }

    fun setDesc(): String {
        return desc
    }

    fun setIdTeam(): String {
        return idPlayer
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to teamData.idTeam,
                        FavoriteTeam.TEAM_NAME to teamData.strTeam,
                        FavoriteTeam.TEAM_BADGE to teamData.strTeamBadge,
                        FavoriteTeam.TEAM_YEAR to teamData.intFormedYear,
                        FavoriteTeam.TEAM_STADIUM to teamData.strStadium,
                        FavoriteTeam.TEAM_DESCRIPTION to teamData.strDescriptionEN,
                        FavoriteTeam.TEAM_SPORT to teamData.strSport)
            }
            snackbar(progressBar, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        "(" + FavoriteTeam.TEAM_ID + "={team_id})",
                        "team_id" to teamData.idTeam
                )
            }
            snackbar(progressBar, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            ivAddFavorite.visibility = View.GONE
            ivAddedFavorite.visibility = View.VISIBLE
        } else {
            ivAddFavorite.visibility = View.VISIBLE
            ivAddedFavorite.visibility = View.GONE
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to teamData.idTeam)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }

    }

}
