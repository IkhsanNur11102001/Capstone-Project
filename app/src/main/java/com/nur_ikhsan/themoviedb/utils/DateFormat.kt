package com.nur_ikhsan.themoviedb.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    fun formatDate(date : String, format : String) : String{
        var formatDate = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val parseDate = sdf.parse(date)
            formatDate = SimpleDateFormat(format).format(parseDate)
        }catch (e : Exception){
            e.printStackTrace()
        }
        return formatDate
    }
}