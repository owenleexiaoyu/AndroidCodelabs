package life.lixiaoyu.jetpackpractice.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LocationManagerWithLifecycle(val callback: LocationCallback) : DefaultLifecycleObserver {

    private var isActive: Boolean = false

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        checkActive(owner.lifecycle)
        startGetLocation()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        checkActive(owner.lifecycle)
        stopGetLocation()
    }

    fun startGetLocation() {
        Log.d(TAG, "LocationManagerWithLifecycle startGetLocation")
        calculateLocation()
    }

    fun stopGetLocation() {
        Log.d(TAG, "LocationManagerWithLifecycle stopGetLocation")
    }

    private fun checkActive(lifecycle: Lifecycle) {
        isActive = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
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

    companion object {
        const val TAG = "OWEN_LIFECYCLE"
    }
}