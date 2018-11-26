package com.jelekong.footballmatchschedule.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strSport")
        var strSport: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null,

        @SerializedName("strThumb")
        var strThumb: String? = null,

        @SerializedName("strHeight")
        var strHeight: String,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null

) : Parcelable