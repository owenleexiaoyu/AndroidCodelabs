package life.lixiaoyu.jetpackpractice.lifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class LifecycleDemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_demo)
        supportActionBar?.title = "Lifecycle"

        findViewById<Button>(R.id.btn_without_lifecycle).setOnClickListener {
            startActivity(Intent(this, GetLocationWithoutLifecycleActivity::class.java))
        }
        findViewById<Button>(R.id.btn_with_lifecycle).setOnClickListener {
            startActivity(Intent(this, GetLocationWithLifecycleActivity::class.java))
        }
        findViewById<Button>(R.id.btn_with_lifecycle_service).setOnClickListener {
            startActivity(Intent(this, GetLocationWithLifectcleServiceActivity::class.java))
        }
    }
}