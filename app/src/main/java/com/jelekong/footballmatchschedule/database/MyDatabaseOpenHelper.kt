package com.jelekong.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.EVENT_ID to TEXT + PRIMARY_KEY,
                Favorite.EVENT_DATE to TEXT,
                Favorite.EVENT_HOME_NAME to TEXT,
                Favorite.EVENT_AWAY_NAME to TEXT,
                Favorite.EVENT_HOME_SCORE to TEXT,
                Favorite.EVENT_AWAY_SCORE to TEXT,
                Favorite.EVENT_HOME_GOALS to TEXT,
                Favorite.EVENT_AWAY_GOALS to TEXT,
                Favorite.EVENT_HOME_SHOTS to TEXT,
                Favorite.EVENT_AWAY_SHOTS to TEXT,
                Favorite.EVENT_HOME_GOALS_KEEPER to TEXT,
                Favorite.EVENT_AWAY_GOALS_KEEPER to TEXT,
                Favorite.EVENT_HOME_DEFENS to TEXT,
                Favorite.EVENT_AWAY_DEFENS to TEXT,
                Favorite.EVENT_HOME_MIDFIELD to TEXT,
                Favorite.EVENT_AWAY_MIDFIELD to TEXT,
                Favorite.EVENT_HOME_GOALS_FORWARD to TEXT,
                Favorite.EVENT_AWAY_GOALS_FORWARD to TEXT,
                Favorite.EVENT_HOME_GOALS_SUBSTITUTES to TEXT,
                Favorite.EVENT_AWAY_GOALS_SUBSTITUTES to TEXT,
                Favorite.EVENT_HOME_TEAM_ID to TEXT,
                Favorite.EVENT_AWAY_TEAM_ID to TEXT,
                Favorite.EVENT_TIME to TEXT,
                Favorite.EVENT_SPORT to TEXT)

        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.TEAM_ID to TEXT + PRIMARY_KEY,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT,
                FavoriteTeam.TEAM_YEAR to TEXT,
                FavoriteTeam.TEAM_STADIUM to TEXT,
                FavoriteTeam.TEAM_DESCRIPTION to TEXT,
                FavoriteTeam.TEAM_SPORT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)