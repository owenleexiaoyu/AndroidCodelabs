package life.lixiaoyu.androidmaster.hirestful

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import life.lixiaoyu.androidmaster.R
import life.lixiaoyu.hirestful.HiCallback
import life.lixiaoyu.hirestful.HiResponse
import life.lixiaoyu.hirestful.HiRestful
import life.lixiaoyu.hirestful.volley.VolleyCallFactory

class HiRestfulDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restful)

        val baseUrl = "https://www.wanandroid.com"
        // 使用 Okhttp
//        val okHttpCallFactory = OkHttpCallFactory()
//        val hirestful = HiRestful(baseUrl, okHttpCallFactory)
        // 使用 Retrofit
//        val retrofitCallFactory = RetrofitCallFactory(baseUrl = baseUrl)
//        val hirestful = HiRestful(baseUrl, retrofitCallFactory)
        // 使用 Volley
        val volleyCallFactory = VolleyCallFactory(this)
        val hirestful = HiRestful(baseUrl, volleyCallFactory)
        hirestful.addInterceptor(BizInterceptor())

        val apiService = hirestful.create(WanAndroidApi::class.java)
        apiService.getBannerList()
            .enqueue(object : HiCallback<List<Banner>> {
                override fun onSuccess(response: HiResponse<List<Banner>>) {
                    Log.d("GETBANNERLIST", "Success: ${response.data}")
                }

                override fun onFailed(throwable: Throwable) {
                    Log.d("GETBANNERLIST", "Error: ${throwable.message}")
                }

            })

    }
}