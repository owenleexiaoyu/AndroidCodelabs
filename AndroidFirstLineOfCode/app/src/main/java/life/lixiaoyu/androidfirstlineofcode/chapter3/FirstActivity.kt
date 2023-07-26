package life.lixiaoyu.androidfirstlineofcode.chapter3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidfirstlineofcode.R

class FirstActivity: AppCompatActivity() {

    private val tag = "FirstActivity"

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)
        Log.d(tag, "onCreate")
        button1 = findViewById(R.id.button1)
        button1.text = "Show Toast"
        button1.setOnClickListener {
            Toast.makeText(
                this@FirstActivity,
                "Click Button 1",
                Toast.LENGTH_SHORT
            ).show()
        }

        button2 = findViewById(R.id.button2)
        button2.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        button3 = findViewById(R.id.button3)
        button3.setOnClickListener {
            val intent = Intent("life.lixiaoyu.androidfirstlineofcode.ACTION_START")
            intent.addCategory("life.lixiaoyu.androidfirstlineofcode.MY_CATEGORY")
            startActivity(intent)
        }

        button4 = findViewById(R.id.button4)
        button4.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
        }

        button5 = findViewById(R.id.button5)
        button5.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }

        button6 = findViewById(R.id.button6)
        button6.setOnClickListener {
            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }
}