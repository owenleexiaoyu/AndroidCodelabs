package life.lixiaoyu.androidfirstlineofcode.chapter4

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidfirstlineofcode.R

class ButtonDemoActivity: AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_demo)
        button = findViewById(R.id.cp_button)
        button.setOnClickListener {
            Toast.makeText(this, "You clicked a Button", Toast.LENGTH_SHORT).show()
        }
    }
}