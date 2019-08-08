package match.football.submission2.submission2.app.util

import java.text.SimpleDateFormat
import java.util.*

object Time {
    fun timeStamp(date: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = formatter.parse(date) as Date
        return date.time
    }

    fun formatUTCtoGMT(date: String, time: String, format: String): String {
        val date = "$date $time +0000"
        val oldFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm Z")
        oldFormatter.timeZone = TimeZone.getTimeZone("UTC")
        val parser = SimpleDateFormat(format)
        val value = oldFormatter.parse(date)
        parser.timeZone = TimeZone.getDefault()
        val result = parser.format(value)
        return result
    }


}