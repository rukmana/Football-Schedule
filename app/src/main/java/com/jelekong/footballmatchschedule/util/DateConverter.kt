package com.jelekong.footballmatchschedule.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {
        const val DATE_24_HOURS = "yyyy-MM-dd HH:mm:ss"
        const val DATE_WITHOUT_HOURS = "yyyy-MM-dd"


        fun format(value: Date, pattern: String? = DATE_24_HOURS): String {
            return try {
                val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
                dateFormat.timeZone = TimeZone.getDefault()
                dateFormat.format(value)
            } catch (e: ParseException) {
                val dateFormat = SimpleDateFormat(DATE_WITHOUT_HOURS, Locale.getDefault())
                dateFormat.timeZone = TimeZone.getDefault()
                dateFormat.format(value)
            }
        }

        fun formatGMT(date: String?, time: String?): Date? {
            val formatter = SimpleDateFormat(DATE_24_HOURS)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return formatter.parse(dateTime)
        }

        fun String.formatDate(format: String = "yyyy-MM-dd HH:mm:ss"): Long {

            val formatter = SimpleDateFormat(format, Locale.ENGLISH)
            val date = formatter.parse(this)

            return date.time
        }
    }
}