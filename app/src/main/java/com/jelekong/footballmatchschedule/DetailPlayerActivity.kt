package com.jelekong.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jelekong.footballmatchschedule.response.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        val actionbar = supportActionBar
        actionbar!!.title = resources.getString(R.string.detail_Player)
        actionbar.setDisplayHomeAsUpEnabled(true)

        player = intent.getParcelableExtra<Player>("detailPlayer")

        Picasso.get().load(player.strThumb).into(iv_detail_player)
        tv_detail_weight.text = player.strWeight
        tv_detail_height.text = player.strHeight
        tv_desc_player.text = player.strDescriptionEN
        tv_detail_position.text = player.strPosition
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}