package life.lixiaoyu.jetpackpractice.viewmodel1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R
import java.util.Timer
import java.util.TimerTask

class TimerActivityWithoutViewModel: AppCompatActivity() {

    private var tvTimer: TextView? = null

    private var timer: Timer? = null
    private var currentSecond: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSecond = savedInstanceState?.getInt(CURRENT_TIME_IN_SECOND) ?: 0
        setContentView(R.layout.activity_viewmodel1_timer)
        tvTimer = findViewById(R.id.tv_timer)
        startTiming()
    }

    private fun startTiming() {
        if (timer == null) {
            timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    currentSecond++
                    runOnUiThread {
                        tvTimer?.text = "Timer: $currentSecond"
                    }
                }
            }
            timer?.schedule(timerTask, 1000, 1000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_TIME_IN_SECOND, currentSecond)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    companion object {
        const val CURRENT_TIME_IN_SECOND = "current_time_in_second"
    }
}