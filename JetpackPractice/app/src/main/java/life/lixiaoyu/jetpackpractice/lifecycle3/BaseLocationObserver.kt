package life.lixiaoyu.jetpackpractice.lifecycle3

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

open class BaseLocationObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun doOnCreate() {
        Log.d("OWEN", "BaseLocationObserver doOnCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun doOnDestroy() {
        Log.d("OWEN", "BaseLocationObserver doOnDestroy")
    }
}