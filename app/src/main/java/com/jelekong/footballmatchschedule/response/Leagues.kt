package com.jelekong.footballmatchschedule.response

import com.google.gson.annotations.SerializedName

data class Leagues(
        @SerializedName("idLeague")
        val idLeague: String?,

        @SerializedName("strLeague")
        val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
