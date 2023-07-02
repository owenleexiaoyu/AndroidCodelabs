package life.lixiaoyu.jetpackpractice.livedata1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R

class TimerActivityWithViewModelAndLiveData: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel1_timer)
        initComponent()
    }

    private fun initComponent() {
        val tvTimer: TextView = findViewById(R.id.tv_timer)
        val timerViewModel = ViewModelProvider(this).get(TimerViewModelWithLiveData::class.java)
        timerViewModel.currentSecond.observe(this) {
            tvTimer.text = "Timer: $it"
        }
        timerViewModel.startTiming()
    }
}