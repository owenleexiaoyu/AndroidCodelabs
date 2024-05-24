package life.lixiaoyu.helloandroid.chapter4

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class ProgressBarDemoActivity: AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar1: ProgressBar
    private lateinit var button: Button
    private lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressbar_demo)
        progressBar = findViewById(R.id.circular_progress)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            if (progressBar.visibility == View.VISIBLE) {
                progressBar.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.VISIBLE
            }
        }

        progressBar1 = findViewById(R.id.horizontal_progress)
        button1 = findViewById(R.id.button1)
        button1.setOnClickListener {
            progressBar1.progress = progressBar1.progress + 10
        }
    }
}