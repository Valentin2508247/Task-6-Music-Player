package com.valentin.musicplayer.utils

object TimeUtils {

    fun timeStr(time: Long): String {
        var seconds = time / 1000
        val minutes = seconds / 60 % 60
        seconds %= 60
        val secStr = if (seconds < 10) "0$seconds" else "$seconds"
        val minStr = if (minutes < 10) "0$minutes" else "$minutes"
        return "$minStr:$secStr"
    }

    const val startTime = "00:00"
}