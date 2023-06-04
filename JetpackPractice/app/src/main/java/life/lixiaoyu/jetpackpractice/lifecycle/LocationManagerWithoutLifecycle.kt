package life.lixiaoyu.jetpackpractice.lifecycle

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import life.lixiaoyu.jetpackpractice.lifecycle.LocationManagerWithLifecycle.Companion.TAG
import java.util.concurrent.TimeUnit

class LocationManagerWithoutLifecycle(val callback: LocationCallback) {

    private var isActive = false

    fun startGetLocation() {
        isActive = true
        Log.d(TAG, "LocationManagerWithoutLifecycle startGetLocation")
        calculateLocation()
    }

    fun stopGetLocation() {
        isActive = false
        Log.d(TAG, "LocationManagerWithoutLifecycle stopGetLocation")
    }

    private fun calculateLocation() {
        Flowable.just(1.0F to 2.0F)
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (isActive) callback.onLocationReady(it.first, it.second)
            }
    }
}

interface LocationCallback {

    fun onLocationReady(longitude: Float, latitude: Float)
}