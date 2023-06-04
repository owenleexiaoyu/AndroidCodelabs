package life.lixiaoyu.jetpackpractice.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R
import life.lixiaoyu.jetpackpractice.lifecycle.LocationManagerWithLifecycle.Companion.TAG

class GetLocationWithoutLifecycleActivity: AppCompatActivity() {

    var locationManager: LocationManagerWithoutLifecycle? = null
    private var tvShowLocation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_get_location_without_and_with_lifecycle)
        tvShowLocation = findViewById(R.id.tv_show_location)
        locationManager = LocationManagerWithoutLifecycle(object : LocationCallback {
            override fun onLocationReady(longitude: Float, latitude: Float) {
                Log.d(TAG, "GetLocationWithoutLifecycleActivity OnLocationReady")
                tvShowLocation?.text = "Longitude: $longitude, Latitude: $latitude"
            }
        })
    }

    override fun onStart() {
        super.onStart()
        locationManager?.startGetLocation()
    }

    override fun onStop() {
        super.onStop()
        locationManager?.stopGetLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager = null
    }

}