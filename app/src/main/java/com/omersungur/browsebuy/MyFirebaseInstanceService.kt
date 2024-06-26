package com.omersungur.browsebuy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceService:  FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("newToken", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(packageName,
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {  }
                notificationManager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(this,packageName)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(it.title)
                .setContentText(it.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationManager.notify(0,builder.build())
        }
    }
}