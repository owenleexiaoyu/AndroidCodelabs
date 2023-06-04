package life.lixiaoyu.jetpackpractice.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import life.lixiaoyu.jetpackpractice.lifecycle.LocationManagerWithLifecycle.Companion.TAG

class MyApplicationObserver: DefaultLifecycleObserver {

    /**
     * 在应用程序整个生命周期中只会被调用一次
     */
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "MyApplicationObserver onCreate")
    }

    /**
     * 当应用程序在前台出现时被调用
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "MyApplicationObserver onStart")
    }

    /**
     * 当应用程序在前台出现时被调用
     */
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "MyApplicationObserver onResume")
    }

    /**
     * 当应用程序退出到后台时被调用
     */
    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "MyApplicationObserver onPause")
    }

    /**
     * 当应用程序退出到后台时被调用
     */
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "MyApplicationObserver onStop")
    }

    /**
     * 永远不会被调用，不会分发 ON_DESTROY 事件
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "MyApplicationObserver onDestroy")
    }
}