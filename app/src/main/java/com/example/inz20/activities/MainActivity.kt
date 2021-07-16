package com.example.inz20.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.inz20.*
import com.example.inz20.fragments.LeftFragment
import com.example.inz20.fragments.RightFragments
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var logoutIfInBackground = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)

        val adapter = ViewPagerAdapterForFragments(supportFragmentManager)

        adapter.addFragment(LeftFragment(), "Strona Główna")
        adapter.addFragment(RightFragments(), "Ustawienia")

        viewPager.adapter = adapter

//        notificationManagment()

        tlMainMenu.setupWithViewPager(viewPager)
        tlMainMenu.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_emoji_people)
        tlMainMenu.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_settings)

    }

    override fun onResume() {
        super.onResume()
        logoutIfInBackground = true
    }

    override fun onPause() {
        super.onPause()
        if (logoutIfInBackground) {
            val intent = Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }
    }

//    fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID, CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply {
//                lightColor = Color.BLUE
//                enableLights(true)
//            }
//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//    }
//
//    fun notificationManagment() {
//
//        createNotificationChannel()
//
//        val intent = Intent(this, MainActivity::class.java)
//        val pendingIntent = TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//
//    }
}