package life.lixiaoyu.jetpackpractice.lifecycle2

import android.os.CountDownTimer
import android.util.Log

/**
 * 广告引导页计时器
 */
class AdManager {
    var adListener: AdManageListener? = null

    private var countDownTimer: CountDownTimer? = object: CountDownTimer(5000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG, "广告剩余 ${(millisUntilFinished / 1000).toInt()} 秒")
            adListener?.timing((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            Log.d(TAG, "广告结束， 准备进入主界面")
            adListener?.enterMainPage()
        }
    }

    fun start() {
        Log.d(TAG, "开始计时")
        countDownTimer?.start()
    }

    fun cancel() {
        Log.d(TAG, "停止计时")
        countDownTimer?.cancel()
        countDownTimer = null
    }
}

interface AdManageListener {
    /**
     * 计时
     * @param second 秒
     */
    fun timing(second: Int)

    /**
     * 计时结束，进入主页面
     */
    fun enterMainPage()
}

val TAG = "OWEN_LIFECYCLE_2"