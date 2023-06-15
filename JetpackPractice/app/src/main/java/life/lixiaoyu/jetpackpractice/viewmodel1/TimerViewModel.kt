package life.lixiaoyu.jetpackpractice.viewmodel1

import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class TimerViewModel: ViewModel() {

    private var timer: Timer? = null
    private var currentSecond: Int = 0

    var timeChangeListener: OnTimeChangeListener? = null

    fun startTiming() {
        if (timer == null) {
            currentSecond = 0
            timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    currentSecond++
                    timeChangeListener?.onTimeChanged(currentSecond)
                }
            }
            timer?.schedule(timerTask, 1000, 1000)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}

interface OnTimeChangeListener {
    fun onTimeChanged(second: Int)
}