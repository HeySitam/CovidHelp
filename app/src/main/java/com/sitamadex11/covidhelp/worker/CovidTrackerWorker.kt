package com.sitamadex11.covidhelp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.activity.CovidTrackerActivity
import com.sitamadex11.covidhelp.covidTrackerApi.CovidData
import com.sitamadex11.covidhelp.util.ApiUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CovidTrackerWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        ApiUtilities.apiInterface.covidData!!.enqueue(object : Callback<CovidData?> {
            override fun onResponse(call: Call<CovidData?>, response: Response<CovidData?>) {
                val covidData = response.body()
                val time = "${
                    covidData!!.lastRefreshed.substring(
                        0,
                        10
                    )
                } ${covidData.lastRefreshed.substring(11, 19)}"
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${time}")
                Log.d("chk_worker", sdf.toString())
                val totalCount = covidData!!.data.summary.total
                val recovered = covidData!!.data.summary.discharged
                showNotification(totalCount, recovered, getTimeAgo(sdf))
            }

            override fun onFailure(call: Call<CovidData?>, t: Throwable) {
                Toast.makeText(applicationContext, "Error : " + t.message, Toast.LENGTH_SHORT)
                    .show()

            }
        })

        return Result.success()
    }

    private fun showNotification(totalCount: String, recovered: String, time: String) {
        val intent = Intent(context, CovidTrackerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "covid"
        val channelName = "Covid Update"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, R.color.red))
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle("Confirmed : $totalCount | Recovered : $recovered")
            .setContentText("Last Updated : $time")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy hh:mm:ss").format(past).toString()
            }
        }
    }
}