package life.lixiaoyu.jetpackpractice.lifecycle3

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class LocationObserver: BaseLocationObserver() {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun doOnStart() {
        Log.d("OWEN", "LocationObserver doOnStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun doOnStart1(owner: LifecycleOwner) {
        Log.d("OWEN", "LocationObserver doOnStart1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun doOnStart2(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("OWEN", "LocationObserver doOnStart2")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun doOnStart3(owner: LifecycleOwner, event: Lifecycle.Event, extra: String) {
        Log.d("OWEN", "LocationObserver doOnStart2")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun doOnStop() {
        Log.d("OWEN", "LocationObserver doOnStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun doOnAny() {
        Log.d("OWEN", "LocationObserver doOnAny")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun doOnAny1(owner: LifecycleOwner) {
        Log.d("OWEN", "LocationObserver doOnAny1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun doOnAny2(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("OWEN", "LocationObserver doOnAny2")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun doOnAny3(owner: LifecycleOwner, event: Lifecycle.Event, extra: String) {
        Log.d("OWEN", "LocationObserver doOnAny3")
    }
}