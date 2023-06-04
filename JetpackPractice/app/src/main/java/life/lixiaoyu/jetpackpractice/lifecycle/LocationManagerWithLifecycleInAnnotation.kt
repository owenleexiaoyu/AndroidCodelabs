package life.lixiaoyu.jetpackpractice.lifecycle

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import life.lixiaoyu.jetpackpractice.lifecycle.LocationManagerWithLifecycle.Companion.TAG
import java.util.concurrent.TimeUnit

class LocationManagerWithLifecycleInAnnotation(val callback: LocationCallback) : LifecycleObserver {

    private var isActive: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        isActive = true
        startGetLocation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        isActive = false
        stopGetLocation()
    }

    fun startGetLocation() {
        Log.d(TAG, "LocationManagerWithLifecycleInAnnotation startGetLocation")
        calculateLocation()
    }

    fun stopGetLocation() {
        Log.d(TAG, "LocationManagerWithLifecycleInAnnotation stopGetLocation")
    }

    private fun calculateLocation() {
        Flowable.just(3.0F to 4.0F)
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (isActive) callback.onLocationReady(it.first, it.second)
            }
    }
}