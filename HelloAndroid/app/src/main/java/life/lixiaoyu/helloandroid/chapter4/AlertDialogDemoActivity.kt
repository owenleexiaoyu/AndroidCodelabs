package life.lixiaoyu.helloandroid.chapter4

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class AlertDialogDemoActivity: AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertdialog_demo)
        button = findViewById(R.id.cp_button)
        button.setOnClickListener {
            // 展示 Alertdialog
            val dialogBuilder = AlertDialog.Builder(this@AlertDialogDemoActivity).apply {
                setTitle("注销账号？")
                setMessage("注销账号后，您的所有信息将会在 7 天内删除，之后账号将不能登陆")
                setCancelable(false)
                setPositiveButton("确认") { dialog, witch ->
                    Toast.makeText(this@AlertDialogDemoActivity, "账号已删除", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("取消") { dialog, witch ->
                }
            }
            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }

    }

}