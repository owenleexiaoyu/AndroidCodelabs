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
        setContentView(R.layout.activity_viewmodel1_timer)
        tvTimer = findViewById(R.id.tv_timer)
        startTiming()
    }

    private fun startTiming() {
        if (timer == null) {
            currentSecond = 0
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

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}