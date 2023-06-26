package life.lixiaoyu.androidfirstlineofcode.chapter3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidfirstlineofcode.R

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}