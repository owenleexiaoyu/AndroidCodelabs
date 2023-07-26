package life.lixiaoyu.androidfirstlineofcode.chapter4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidfirstlineofcode.R

class WidgetsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets)
        findViewById<Button>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, TextViewDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, ButtonDemoActivity::class.java))
        }
    }
}