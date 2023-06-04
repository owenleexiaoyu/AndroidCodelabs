package life.lixiaoyu.jetpackpractice.lifecycle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class GetLocationWithLifectcleServiceActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MyService::class.java)
        setContentView(R.layout.activity_lifecycle_get_location_with_lifecycle_service)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            startService(intent)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            stopService(intent)
        }
    }
}