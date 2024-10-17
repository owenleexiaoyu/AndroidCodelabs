package life.lixiaoyu.androidmaster.hiexecutor

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidmaster.R
import life.lixiaoyu.hiexecutor.HiExecutor

class HiExecutorDemoActivity: AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button

    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_executor)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        button1.setOnClickListener {
            for (priority: Int in 0 until 10) {
                val finalPriority = priority
                HiExecutor.execute(finalPriority) {
                    try {
                        Thread.sleep(1000L - finalPriority * 100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        button2.setOnClickListener {
            if (isPaused) {
                HiExecutor.resume()
            } else {
                HiExecutor.pause()
            }
            isPaused = !isPaused
        }
        button3.setOnClickListener {
            HiExecutor.execute(callable = object : HiExecutor.Callable<String>() {
                override fun onBackground(): String {
                    Log.d(TAG, "onBackground-当前线程是：${Thread.currentThread().name}")
                    return "我是异步任务结果"
                }

                override fun onCompleted(t: String) {
                    Log.d(TAG, "onCompleted-当前线程是：${Thread.currentThread().name}")
                    Log.d(TAG, "onCompleted-结果是：${t}")
                }

            })
        }

    }

    companion object {
        private const val TAG = "HiExecutorDemoActivity"
    }

}