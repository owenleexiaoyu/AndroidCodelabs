package life.lixiaoyu.helloandroid.network.okhttp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R
import life.lixiaoyu.helloandroid.network.entity.Banner
import life.lixiaoyu.helloandroid.network.entity.WanResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread

class OkHttpDemoActivity: AppCompatActivity() {
    private lateinit var getBannerBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var bannerInfo: TextView

    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(LogInterceptor())
        .addInterceptor(AddHeaderInterceptor())
        .addNetworkInterceptor(LogInterceptor())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        getBannerBtn = findViewById(R.id.get_banner_info)
        bannerInfo = findViewById(R.id.banner_list)
        loginBtn = findViewById(R.id.login)

        getBannerBtn.setOnClickListener {
            getBannerInfoAsync()
//            getBannerInfoSync()
//            getBannerInfoAsyncWithOkHttpEngine()
        }
        loginBtn.setOnClickListener {
//            loginWanAndroidJSON()
            loginWanAndroidForm()
        }
        findViewById<Button>(R.id.upload_image).setOnClickListener {
//            uploadImageToSMMS()
        }
    }

    private fun loginWanAndroidForm() {
        val requestBody = FormBody.Builder()
            .add("username", "owenleexiaoyu")
            .add("password", "Owenhaishu233")
            .build()
        val request = Request.Builder()
            .url("https://www.wanandroid.com/user/login")
            .post(requestBody)
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@OkHttpDemoActivity, "Login failed, reason: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 注意 response.body?.string() 对于结果的解析也应该在子线程中
                // 放在主线程中会抛出 NetworkOnMainThreadException 异常
                val responseData = response.body?.string() ?: ""
                val responseJsonObject = JSONObject(responseData)
                val errorCode = responseJsonObject.optInt("errorCode")
                val errorMsg = responseJsonObject.optString("errorMsg")
                runOnUiThread {
                    if (errorCode == 0) {
                        Toast.makeText(this@OkHttpDemoActivity, "Login success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@OkHttpDemoActivity, "Login failed, reason: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun loginWanAndroidJSON() {
        val username = "owenleexiaoyu"
        val password = "Owenhaishu233"
        val jsonObj = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        val params = jsonObj.toString()
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = params.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://www.wanandroid.com/user/login")
            .post(requestBody)
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@OkHttpDemoActivity, "Login failed, reason: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 注意 response.body?.string() 对于结果的解析也应该在子线程中
                // 放在主线程中会抛出 NetworkOnMainThreadException 异常
                val responseData = response.body?.string() ?: ""
                val responseJsonObject = JSONObject(responseData)
                val errorCode = responseJsonObject.optInt("errorCode")
                val errorMsg = responseJsonObject.optString("errorMsg")
                runOnUiThread {
                    if (errorCode == 0) {
                        Toast.makeText(this@OkHttpDemoActivity, "Login success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@OkHttpDemoActivity, "Login failed, reason: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun getBannerInfoAsync() {
        val request = Request.Builder()
            .url("http://wanandroid.com/banner/json")
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

    private fun getBannerInfoAsyncWithOkHttpEngine() {
        OkHttpEngine.get(this)
            .getAsync("https://wanandroid.com/banner/json", object: HttpCallback<WanResponse<List<Banner>>>() {
                override fun onSuccess(result: WanResponse<List<Banner>>, rawResponse: Response) {
//                    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
//                    scope.launch {
//                        val responseData = withContext(Dispatchers.IO) {
//                            rawResponse.body?.string() ?: "Empty response"
//                        }
//                        bannerInfo.text = responseData
//                    }
                    bannerInfo.text = result.data.toString()
                }

                override fun onFail(request: Request, exception: Exception) {
                    bannerInfo.text = "Request fail, exception = ${exception.message}"
                }
            })
    }

    private fun getBannerInfoSync() {
        val request = Request.Builder()
            .url("https://wanandroid.com/banner/json")
            .get()
            .build()
        val call = okHttpClient.newCall(request)
        thread {
            try {
                val response = call.execute()
                val responseData = response.body?.string() ?: "Empty response"
                runOnUiThread {
                    bannerInfo.text = responseData
                }
            } catch (e: IOException) {
                runOnUiThread {
                    bannerInfo.text = "Request fail, exception = ${e.message}"
                }
            }
        }
    }
}