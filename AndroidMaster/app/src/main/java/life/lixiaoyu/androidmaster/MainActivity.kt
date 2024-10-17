package life.lixiaoyu.androidmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidmaster.hiexecutor.HiExecutorDemoActivity
import life.lixiaoyu.androidmaster.hirestful.HiRestfulDemoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
//    private lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
//        button3 = findViewById(R.id.button3)

        button1.setOnClickListener {
            startActivity(Intent(this@MainActivity, HiRestfulDemoActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, HiExecutorDemoActivity::class.java))
        }
    }
}