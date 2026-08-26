package com.guzelduatr.app.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    fun createChannel(context: Context) {
        val channel = NotificationChannel("prayer_channel", "Namaz Hatırlatmaları", NotificationManager.IMPORTANCE_HIGH)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
    }
}
