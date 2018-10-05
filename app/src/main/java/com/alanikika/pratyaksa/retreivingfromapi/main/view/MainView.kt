package com.alanikika.pratyaksa.retreivingfromapi.main.view

import com.alanikika.pratyaksa.retreivingfromapi.model.Team

interface MainView {

    fun showProgressBar()
    fun hideProgressBar()
    fun showTeamList(data: List<Team>)

}