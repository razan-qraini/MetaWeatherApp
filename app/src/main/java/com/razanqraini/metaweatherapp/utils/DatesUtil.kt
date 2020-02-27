package com.razanqraini.metaweatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DatesUtil {

    // The format of the date received in API response
    private const val API_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val HOURS_12 = "hh:mm a"
    const val DATE_TIME_FORMAT = "EEE, MMM dd | hh:mm a"
    const val DATE_FORMAT = "MMM dd, yyyy"


    private val locale = Locale.ENGLISH

    private fun parseDate(date: String, format: String): Date {
        return SimpleDateFormat(format, locale).parse(date)
    }

    @JvmStatic
    fun formatDate(date: Date, format: String): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(date)
    }

    /**
     * Format date to be displayed in this format: Feb 25, 2020 | 5:28 pm"
     * @param date: view date
     */
    @JvmStatic
    fun formatDateAtTime(date: String): String {
        return formatDate(date.toDate(API_DATE), DATE_TIME_FORMAT)
    }

    /**
     * Returns the hour in 12-Format from the given [date].
     * Default date format is [DatesUtil.API_DATE].
     */
    fun getHourIn12Format(date: String, format: String = API_DATE): String {
        return formatDate(parseDate(date, format), HOURS_12)
    }

    fun addDayBy(date: Date, by: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_MONTH, by)
        return cal.time
    }

    private fun String.toDate(format: String): Date {
        return parseDate(this, format)
    }

}
