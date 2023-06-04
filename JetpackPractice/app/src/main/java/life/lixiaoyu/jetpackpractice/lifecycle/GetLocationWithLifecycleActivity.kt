package life.lixiaoyu.jetpackpractice.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class GetLocationWithLifecycleActivity: AppCompatActivity() {

    private var tvShowLocation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_get_location_without_and_with_lifecycle)
        tvShowLocation = findViewById(R.id.tv_show_location)
        lifecycle.addObserver(LocationManagerWithLifecycle(object : LocationCallback {
            override fun onLocationReady(longitude: Float, latitude: Float) {
                Log.d(LocationManagerWithLifecycle.TAG, "GetLocationWithLifecycleActivity OnLocationReady")
                tvShowLocation?.text = "Longitude: $longitude, Latitude: $latitude"
            }
        }))
    }
}