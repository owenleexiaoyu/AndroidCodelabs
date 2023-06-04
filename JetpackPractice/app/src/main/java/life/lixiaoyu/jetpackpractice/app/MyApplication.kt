package life.lixiaoyu.jetpackpractice.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import life.lixiaoyu.jetpackpractice.lifecycle.MyApplicationObserver

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyApplicationObserver())
    }
}