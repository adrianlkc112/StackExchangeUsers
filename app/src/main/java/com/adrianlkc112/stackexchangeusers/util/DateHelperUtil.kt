package com.adrianlkc112.stackexchangeusers.util

import java.text.SimpleDateFormat
import java.util.*


object DateHelperUtil {

    fun convertEpochToDisplay(epoch: Long?,
                                   format: String = "yyyy-MM-dd HH:mm:ss",
                                   locale: Locale = Locale.ENGLISH): String {
        if(epoch != null) {
            try {
                val outDateFormat = SimpleDateFormat(format, locale)
                return outDateFormat.format(Date(epoch * 1000))
            } catch (e: IllegalArgumentException) {
                LogE(e.toString())
            }
        }

        return ""
    }
}