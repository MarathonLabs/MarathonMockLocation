package io.marathonlabs.location.mock

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.provider.ProviderProperties

class MockLocationProvider(private val context: Context) {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun enableMockProvider(providerName: String) {
        locationManager.addTestProvider(
            providerName,
            false,  // requiresNetwork
            false,  // requiresSatellite
            false,  // requiresCell
            false,  // hasMonetaryCost
            true,   // supportsAltitude
            true,   // supportsSpeed
            true,   // supportsBearing
            ProviderProperties.POWER_USAGE_LOW,
            ProviderProperties.ACCURACY_FINE
        )
        locationManager.setTestProviderEnabled(providerName, true)
    }

    fun setMockLocation(providerName: String, latitude: Double, longitude: Double, accuracy: Float = 5.0f) {
        val mockLocation = Location(providerName).apply {
            this.latitude = latitude
            this.longitude = longitude
            this.accuracy = accuracy
            this.altitude = 0.0
            this.time = System.currentTimeMillis()
            this.elapsedRealtimeNanos = System.nanoTime()
        }
        locationManager.setTestProviderLocation(providerName, mockLocation)
    }

    fun disableMockProvider(providerName: String) {
        locationManager.setTestProviderEnabled(providerName, false)
        locationManager.removeTestProvider(providerName)
    }
}
