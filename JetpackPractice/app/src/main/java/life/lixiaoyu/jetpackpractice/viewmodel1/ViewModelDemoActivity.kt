package life.lixiaoyu.jetpackpractice.viewmodel1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class ViewModelDemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel1_demo)
        supportActionBar?.title = "ViewModel"

        findViewById<Button>(R.id.timer_without_viewmodel).setOnClickListener {
            startActivity(Intent(this, TimerActivityWithoutViewModel::class.java))
        }
        findViewById<Button>(R.id.timer_with_viewmodel).setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }
    }
}