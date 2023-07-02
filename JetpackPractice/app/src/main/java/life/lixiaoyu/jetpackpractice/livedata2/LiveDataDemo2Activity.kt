package life.lixiaoyu.jetpackpractice.livedata2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R
import life.lixiaoyu.jetpackpractice.livedata2.student.StudentActivity
import life.lixiaoyu.jetpackpractice.viewmodel3.AdActivityWithViewModelSavedState

class LiveDataDemo2Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata2_demo)
        supportActionBar?.title = "LiveData2"

        findViewById<Button>(R.id.btn_ad_with_livedata).setOnClickListener {
            startActivity(Intent(this, AdActivityWithLiveData::class.java))
        }
        findViewById<Button>(R.id.btn_student).setOnClickListener {
            startActivity(Intent(this, StudentActivity::class.java))
        }
    }
}