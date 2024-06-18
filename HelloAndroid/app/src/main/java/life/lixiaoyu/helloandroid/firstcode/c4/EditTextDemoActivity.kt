package life.lixiaoyu.helloandroid.firstcode.c4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class EditTextDemoActivity: AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittext_demo)
        editText = findViewById(R.id.et_input)
        button = findViewById(R.id.btn_show_input)
        button.setOnClickListener {
            val text = editText.text.toString()
            val message = if (text.isEmpty()) {
                "You didn't type anything"
            } else {
                text
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}