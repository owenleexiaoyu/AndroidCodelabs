package life.lixiaoyu.helloandroid.firstcode.c4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

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
        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, EditTextDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(this, ImageViewDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            startActivity(Intent(this, ProgressBarDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            startActivity(Intent(this, AlertDialogDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            startActivity(Intent(this, FrameLayoutDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            startActivity(Intent(this, LinearLayoutDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            startActivity(Intent(this, RelativeLayoutDemoActivity::class.java))
        }
        findViewById<Button>(R.id.button10).setOnClickListener {
            startActivity(Intent(this, RecyclerViewDemoActivity::class.java))
        }
    }
}