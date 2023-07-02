package life.lixiaoyu.jetpackpractice.livedata1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class LiveDataDemo1Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata1_demo)
        supportActionBar?.title = "LiveData1"

        findViewById<Button>(R.id.btn_timer_with_livedata).setOnClickListener {
            startActivity(Intent(this, TimerActivityWithViewModelAndLiveData::class.java))
        }
        findViewById<Button>(R.id.btn_seekbar).setOnClickListener {
            startActivity(Intent(this, SeekBarActivity::class.java))
        }
    }
}