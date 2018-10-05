package com.alanikika.pratyaksa.retreivingfromapi.main.presenter

import com.alanikika.pratyaksa.retreivingfromapi.api.ApiRepository
import com.alanikika.pratyaksa.retreivingfromapi.api.TheSportDBApi
import com.alanikika.pratyaksa.retreivingfromapi.main.view.MainView
import com.alanikika.pratyaksa.retreivingfromapi.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getTeamList(leagueName: String){
        view.showProgressBar()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(leagueName)),
                    TeamResponse::class.java
                    )

            uiThread {
                view.hideProgressBar()
                view.showTeamList(data.teams)
            }
        }
    }
}