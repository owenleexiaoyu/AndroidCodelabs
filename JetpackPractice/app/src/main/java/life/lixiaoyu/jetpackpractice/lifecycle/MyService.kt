package life.lixiaoyu.jetpackpractice.lifecycle

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import life.lixiaoyu.jetpackpractice.lifecycle.LocationManagerWithLifecycle.Companion.TAG

class MyService : LifecycleService() {

    init {
        lifecycle.addObserver(LocationManagerWithLifecycleInAnnotation(object : LocationCallback {
            override fun onLocationReady(longitude: Float, latitude: Float) {
                Log.d(TAG, "MyService onLocationReady, longitude = $longitude, latitude = $latitude")
            }
        }))
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MyService onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d(TAG, "MyService onStart")
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
        Log.d(TAG, "MyService onBind")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MyService onDestroy")
    }
}