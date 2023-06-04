package life.lixiaoyu.jetpackpractice.lifecycle2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class Lifecycle2DemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle2_demo)
        supportActionBar?.title = "Lifecycle2"

        findViewById<Button>(R.id.btn_ad_without_lifecycle).setOnClickListener {
            startActivity(Intent(this, AdActivityWithoutLifecycle::class.java))
        }
        findViewById<Button>(R.id.btn_ad_with_lifecycle).setOnClickListener {
            startActivity(Intent(this, AdActivityWithLifecycle::class.java))
        }
        findViewById<Button>(R.id.btn_ad_with_custom_lifecycle_owner).setOnClickListener {
            startActivity(Intent(this, AdActivityCustomLifecycleOwner::class.java))
        }
    }
}