package kaan.tabcorp.utilities

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtils {
    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val READABLE_DATE_FORMAT = "dd MMM yyyy"
    private const val DEFAULT_TIME_ZONE = "UTC"

    fun Date.toReadableDate(): String = SimpleDateFormat(READABLE_DATE_FORMAT, Locale.getDefault()).format(this)

    fun getCurrentTime() = System.currentTimeMillis()
    fun getIntervalInDays(fromTimeInMillis: Long, toTimeInMillis: Long): Long {
        val timeDifferenceInMillis = toTimeInMillis - fromTimeInMillis
        return TimeUnit.MILLISECONDS.toDays(timeDifferenceInMillis)
    }
}
