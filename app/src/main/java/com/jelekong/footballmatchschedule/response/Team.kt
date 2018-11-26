package com.jelekong.footballmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        @SerializedName("idTeam")
        var idTeam: String,

        @SerializedName("strTeam")
        var strTeam: String? = null,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String? = null,

        @SerializedName("intFormedYear")
        var intFormedYear: String? = null,

        @SerializedName("strStadium")
        var strStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @SerializedName("strSport")
        var strSport: String
) : Parcelable