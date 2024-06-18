package life.lixiaoyu.helloandroid.network.okhttp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class OkHttpDemoActivity: AppCompatActivity() {
    private lateinit var getBannerBtn: Button
    private lateinit var bannerInfo: TextView

    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        getBannerBtn = findViewById(R.id.get_banner_info)
        bannerInfo = findViewById(R.id.banner_list)

        getBannerBtn.setOnClickListener {
            getBannerInfo()
        }

    }

    private fun getBannerInfo() {
        val request = Request.Builder()
            .url("https://wanandroid.com/banner/json")
            .get()
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    bannerInfo.text = "Request fail, exception = ${e.message}"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 注意 response.body?.string() 对于结果的解析也应该在子线程中
                // 放在主线程中会抛出 NetworkOnMainThreadException 异常
                val responseData = response.body?.string() ?: "Empty response"
                runOnUiThread {
                    bannerInfo.text = responseData
                }
            }
        })
    }
}