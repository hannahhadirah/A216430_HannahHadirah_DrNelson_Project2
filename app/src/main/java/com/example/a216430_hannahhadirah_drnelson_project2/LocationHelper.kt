package com.example.a216430_hannahhadirah_drnelson_project2

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.*

class LocationHelper(private val context: Context) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onResult: (Double, Double) -> Unit) {

        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        ).build()

        fusedLocationClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {

                    val location = result.lastLocation

                    if (location != null) {
                        onResult(location.latitude, location.longitude)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            },
            context.mainLooper
        )
    }
}