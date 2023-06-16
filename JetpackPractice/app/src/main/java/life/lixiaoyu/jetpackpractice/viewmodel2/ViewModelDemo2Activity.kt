package life.lixiaoyu.jetpackpractice.viewmodel2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class ViewModelDemo2Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel2_demo)
        supportActionBar?.title = "ViewModel"

        findViewById<Button>(R.id.ad_without_viewmodel).setOnClickListener {
            startActivity(Intent(this, AdActivityWithoutViewModel::class.java))
        }
        findViewById<Button>(R.id.ad_with_viewmodel).setOnClickListener {
            startActivity(Intent(this, AdActivityWithViewModel::class.java))
        }
    }
}