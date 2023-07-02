package life.lixiaoyu.jetpackpractice.livedata2

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 广告引导页计时器
 */
class AdManagerWithLiveData(val adVM: AdViewModelWithLiveData) : LifecycleObserver {

    private var countDownTimer: CountDownTimer? = object: CountDownTimer(adVM.millisInFuture, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG, "广告剩余 ${(millisUntilFinished / 1000).toInt()} 秒")
            adVM.millisInFuture = millisUntilFinished
            adVM.setTimingResult((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            Log.d(TAG, "广告结束， 准备进入主界面")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun start() {
        Log.d(TAG, "开始计时")
        countDownTimer?.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancel() {
        Log.d(TAG, "停止计时")
        countDownTimer?.cancel()
        countDownTimer = null
    }

    companion object {
        private const val TAG = "AdManagerWithLiveData"
    }
}
