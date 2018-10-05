package com.alanikika.pratyaksa.retreivingfromapi.main

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.alanikika.pratyaksa.retreivingfromapi.R.array.league
import com.alanikika.pratyaksa.retreivingfromapi.api.ApiRepository
import com.alanikika.pratyaksa.retreivingfromapi.main.adapter.MainAdapter
import com.alanikika.pratyaksa.retreivingfromapi.main.presenter.MainPresenter
import com.alanikika.pratyaksa.retreivingfromapi.main.view.MainView
import com.alanikika.pratyaksa.retreivingfromapi.model.Team
import com.alanikika.pratyaksa.retreivingfromapi.util.invisible
import com.alanikika.pratyaksa.retreivingfromapi.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView {

    /*LAYOUT*/
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    /*DATA*/
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var mainPresenter: MainPresenter
    private lateinit var mainAdapter: MainAdapter
    private lateinit var leagueName: String


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner ()
            swipeRefresh = swipeRefreshLayout {

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }

        mainAdapter = MainAdapter(teams)
        listTeam.adapter = mainAdapter

        val request = ApiRepository()
        val gson = Gson()
        mainPresenter = MainPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                mainPresenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        swipeRefresh.onRefresh {
            mainPresenter.getTeamList(leagueName)
        }
    }

    override fun showProgressBar() {
        progressBar.visible()
    }

    override fun hideProgressBar() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()

        teams.addAll(data)
        mainAdapter.notifyDataSetChanged()
    }
}
