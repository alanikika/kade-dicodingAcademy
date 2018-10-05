package com.alanikika.pratyaksa.retreivingfromapi.model

import com.google.gson.annotations.SerializedName

data class Team (
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamLogo: String? = null
)