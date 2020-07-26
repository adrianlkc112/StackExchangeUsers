package com.adrianlkc112.stackexchangeusers.util

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


object DateHelperUtil {

    fun convertEpochToDisplay(epoch: Long?,
                                   format: String = "yyyy-MM-dd HH:mm:ss",
                                   locale: Locale = Locale.ENGLISH,
                                    zone: ZoneId = ZoneId.systemDefault()): String {
        if(epoch != null) {
            try {
                val instant = Instant.ofEpochMilli(epoch * 1000)
                val localDateTime = instant.atZone(zone).toLocalDateTime()
                val formatter = DateTimeFormatter.ofPattern(format, locale)
                return localDateTime.format(formatter)
            } catch (e: IllegalArgumentException) {
                LogE(e.toString())
            }
        }

        return ""
    }
}