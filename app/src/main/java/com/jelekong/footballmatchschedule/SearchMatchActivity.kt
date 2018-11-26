package com.jelekong.footballmatchschedule

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.google.gson.Gson
import com.jelekong.footballmatchschedule.R.menu.*
import com.jelekong.footballmatchschedule.adapter.MatchAdapter
import com.jelekong.footballmatchschedule.api.ApiRespository
import com.jelekong.footballmatchschedule.presenter.MatchPresenter
import com.jelekong.footballmatchschedule.response.ListMatch
import com.jelekong.footballmatchschedule.util.DelayedHandler
import com.jelekong.footballmatchschedule.util.EspressoIdlingResource
import com.jelekong.footballmatchschedule.view.MatchView

class SearchMatchActivity : AppCompatActivity(), MatchView {

    private var matchs: MutableList<ListMatch> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var matchPresenter: MatchPresenter
    private var menuItem: Menu? = null
    private lateinit var searchView: SearchView
    private var query: String = ""
    private val searchHandler = DelayedHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        val actionbar = supportActionBar
        actionbar!!.title = resources.getString(R.string.search)
        actionbar.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rv_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MatchAdapter(this, 1, matchs) {
            val intent = Intent(this, DetailMatchActivity::class.java)
            intent.putExtra("detailMatch", it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val request = ApiRespository()
        val gson = Gson()
        matchPresenter = MatchPresenter(this, request, gson)
        matchPresenter.getAllEvent("")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(search_menu, menu)
        menuItem = menu
        val searchItem = menu?.findItem(R.id.search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                searchHandler.doDelayedTask {
                    query = text.toString()
                    matchPresenter.getAllEvent(query)
                }
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun showTeamList(data: List<ListMatch>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        matchs.clear()
        data.forEach {
            if (it.strSport.equals("Soccer")) {
                matchs.add(it)
            }
        }
        adapter.notifyDataSetChanged()
        EspressoIdlingResource.decrement()
    }

}