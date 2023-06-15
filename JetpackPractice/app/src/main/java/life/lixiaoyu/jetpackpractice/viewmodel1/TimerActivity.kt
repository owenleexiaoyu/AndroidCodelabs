package life.lixiaoyu.jetpackpractice.viewmodel1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R

class TimerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel1_timer)
        initComponent()
    }

    private fun initComponent() {
        val tvTimer: TextView = findViewById(R.id.tv_timer)
        val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        timerViewModel.timeChangeListener = object : OnTimeChangeListener {
            override fun onTimeChanged(second: Int) {
                runOnUiThread {
                    tvTimer.text = "Timer: $second"
                }
            }
        }
        timerViewModel.startTiming()
    }
}