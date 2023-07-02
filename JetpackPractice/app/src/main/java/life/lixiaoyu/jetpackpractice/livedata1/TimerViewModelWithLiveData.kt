package life.lixiaoyu.jetpackpractice.livedata1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class TimerViewModelWithLiveData: ViewModel() {

    private var timer: Timer? = null
    private var _currentSecond: MutableLiveData<Int> = MutableLiveData(0)
    val currentSecond: LiveData<Int> = _currentSecond

    fun startTiming() {
        if (timer == null) {
            timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    _currentSecond.postValue(_currentSecond.value!! + 1)
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