package com.valentin.musicplayer.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context

object NotificationUtils {

    fun createNotificationChannel(context: Context) {
        // api always >= 26
        val descriptionText = "Player notifications"
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    const val CHANNEL_ID = "Exoplayer_Notification"
    const val CHANNEL_NAME = "Exoplayer_Notifications"
    const val IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
    const val NOTIFICATION_ID = 2508
}