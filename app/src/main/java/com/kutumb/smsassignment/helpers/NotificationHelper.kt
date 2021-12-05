package com.kutumb.smsassignment.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kutumb.smsassignment.R


class NotificationHelper {

    companion object {
        private const val All_CHANNEL_ID = "Kutumb_SMS_Notification"
        private const val All_CHANNEL = "Kutumb_SMS_Notification"
        private const val DeviceNotificationId = 101

        fun showNotification(
                applicationContext: Context,
                title: String?,
                body: String?,
                activityIntent: Intent
        ) {
            val eventsChannel: NotificationChannel?
            val notificationBuilder: NotificationCompat.Builder?
            val manager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                eventsChannel = NotificationChannel(
                        All_CHANNEL_ID,
                        All_CHANNEL,
                        NotificationManager.IMPORTANCE_HIGH
                )
                manager.createNotificationChannel(eventsChannel)
            }
            notificationBuilder = NotificationCompat.Builder(
                    applicationContext,
                    All_CHANNEL_ID
            )

            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                    applicationContext, 0, activityIntent,
                    PendingIntent.FLAG_ONE_SHOT
            )
            val defaultSoundUri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder //                .setSmallIcon(R.mipmap.app_icon)
                    .setContentTitle(title + "")
                    .setContentText(body + "")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(
                            NotificationCompat.BigTextStyle()
                                    .bigText(body + "")
                    )
                    .setContentIntent(pendingIntent)

            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)

            notificationBuilder.color = ContextCompat.getColor(applicationContext,R.color.colorPrimary)
            val notificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(
                    DeviceNotificationId,
                    notificationBuilder.build()
            )
        }
        fun removeSmsNotification(applicationContext: Context) {
            val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
    }

}
