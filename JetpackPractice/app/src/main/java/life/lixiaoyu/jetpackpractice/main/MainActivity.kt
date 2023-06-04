package life.lixiaoyu.jetpackpractice.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import life.lixiaoyu.jetpackpractice.R

class MainActivity : AppCompatActivity() {

    private var recyclerview: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview?.let {
            it.adapter = MainAdapter()
            it.layoutManager = LinearLayoutManager(this)
        }
    }
}