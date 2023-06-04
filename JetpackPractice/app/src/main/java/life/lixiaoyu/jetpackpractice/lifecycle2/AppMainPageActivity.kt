package life.lixiaoyu.jetpackpractice.lifecycle2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class AppMainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main_page)
    }
}