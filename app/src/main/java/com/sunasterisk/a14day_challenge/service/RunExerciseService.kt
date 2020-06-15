package com.sunasterisk.a14day_challenge.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.core.app.ActivityCompat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class RunExerciseService : Service() {

    private var locationManager: LocationManager? = null
    private var listener: MeasureLocationListener? = null
    private var lat1: Double = 0.0
    private var lng1: Double = 0.0
    private var lat2: Double = 0.0
    private var lng2: Double = 0.0
    private var distance = 0.0
    private var status = 0
    private var intent: Intent? = null
    private val uiHandler: Handler = Handler()

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        uiHandler.removeCallbacks(sendUpdatesToUI)
        uiHandler.postDelayed(sendUpdatesToUI, 0)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        listener = MeasureLocationListener()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            sendBroadcast(intent?.apply {
                putExtra(EXTRA_DISTANCE, EXTRA_ERROR)
            })
            stopSelf()
        }

        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            10000,
            10f,
            listener
        )
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            10000,
            10f,
            listener
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val sendUpdatesToUI = object : Runnable {
        override fun run() {
            intent?.putExtra(EXTRA_DISTANCE, distance.toString())
            sendBroadcast(intent)
            uiHandler.postDelayed(this, TIME_DELAY)
        }
    }

    inner class MeasureLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            when {
                status == 0 -> {
                    lat1 = location.latitude
                    lng1 = location.longitude
                }
                (status % 2) != 0 -> {
                    lat2 = location.latitude
                    lng2 = location.longitude
                    distance += distanceBetweenTwoPoint(lat1, lng1, lat2, lng2)
                }
                (status % 2) == 0 -> {
                    lat1 = location.latitude
                    lng1 = location.longitude
                    distance += distanceBetweenTwoPoint(lat2, lng2, lat1, lng1)
                }
            }
            status++
            uiHandler.postDelayed(sendUpdatesToUI, TIME_DELAY)
        }

        private fun distanceBetweenTwoPoint(
            srcLat: Double,
            srcLng: Double,
            desLat: Double,
            desLng: Double
        ): Int {
            val dLat = Math.toRadians(desLat - srcLat)
            val dLng = Math.toRadians(desLng - srcLng)
            val a = (sin(dLat / 2) * sin(dLat / 2)
                    + (cos(Math.toRadians(srcLat)) * cos(Math.toRadians(desLat))
                    * sin(dLng / 2)
                    * sin(dLng / 2)))
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return (EARTH_RADIUS * c).toInt()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    companion object {
        const val BROADCAST_ACTION = "com.sunasterisk.a14day_challenge.updateUI"
        const val EARTH_RADIUS = 6371000 //meter
        const val TIME_DELAY = 100L //millisecond
        const val EXTRA_DISTANCE = "distance"
        const val EXTRA_ERROR = "error"
    }
}
