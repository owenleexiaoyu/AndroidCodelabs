package life.lixiaoyu.helloandroid.firstcode.c4

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class ButtonDemoActivity: AppCompatActivity() , OnClickListener {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_demo)
        button = findViewById(R.id.cp_button)
        button.setOnClickListener(this)
//        button.setOnClickListener {
//            Toast.makeText(this, "You clicked a Button", Toast.LENGTH_SHORT).show()
//        }
//        button.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                Toast.makeText(this@ButtonDemoActivity, "You clicked a Button", Toast.LENGTH_SHORT).show()
//            }
//        })
//        button.setOnLongClickListener(object: OnLongClickListener {
//            override fun onLongClick(v: View?): Boolean {
//                Toast.makeText(this@ButtonDemoActivity, "You long clicked a Button", Toast.LENGTH_SHORT).show()
//                return true
//            }
//        })
        button.setOnLongClickListener {
            Toast.makeText(this, "You long clicked a Button", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.cp_button) {
            Toast.makeText(this, "You clicked a Button", Toast.LENGTH_SHORT).show()
        }
    }
}