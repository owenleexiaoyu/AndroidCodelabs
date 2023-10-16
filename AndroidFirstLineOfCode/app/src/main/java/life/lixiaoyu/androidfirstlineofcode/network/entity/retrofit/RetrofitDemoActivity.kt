package life.lixiaoyu.androidfirstlineofcode.network.entity.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.androidfirstlineofcode.R
import life.lixiaoyu.androidfirstlineofcode.network.entity.Banner
import life.lixiaoyu.androidfirstlineofcode.network.entity.WanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class RetrofitDemoActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        val tvBannerList = findViewById<TextView>(R.id.banner_list)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WanAndroidApi::class.java)

        api.getBannerList().enqueue(object : Callback<WanResponse<List<Banner>>> {
            override fun onResponse(
                call: Call<WanResponse<List<Banner>>>,
                response: Response<WanResponse<List<Banner>>>
            ) {
                val bannerList = response.body()?.data ?: emptyList()
                val stringBuilder = StringBuilder()
                for (banner in bannerList) {
                    stringBuilder.append("${banner.title}\n")
                    stringBuilder.append("${banner.imagePath}\n\n")
                }
                tvBannerList.text = stringBuilder.toString().trimEnd('\n')
            }

            override fun onFailure(call: Call<WanResponse<List<Banner>>>, t: Throwable) {
                Toast.makeText(this@RetrofitDemoActivity, "Network error", Toast.LENGTH_SHORT).show()
            }

        })
        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            val request = RegisterRequest("xxxxxxxx", "yyyyyyyy", "yyyyyyyy")
            api.register(request).enqueue(object : Callback<WanResponse<String>> {
                override fun onResponse(
                    call: Call<WanResponse<String>>,
                    response: Response<WanResponse<String>>
                ) {
                    Log.d("OWEN", "register onResponse: ${response.body().toString()}")
                }

                override fun onFailure(call: Call<WanResponse<String>>, t: Throwable) {
                    Log.d("OWEN", "register onFailure")
                }

            })
        }

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
//            api.login("xxxxxxxx", "yyyyyyyy").enqueue(object : Callback<WanResponse<String>> {
            val requestMap = mapOf(
                "username" to "xxxxxxxx",
                "password" to "yyyyyyyy"
            )
            api.login(requestMap).enqueue(object : Callback<WanResponse<String>> {
                override fun onResponse(
                    call: Call<WanResponse<String>>,
                    response: Response<WanResponse<String>>
                ) {
                    Log.d("OWEN", "login onResponse: ${response.body().toString()}")
                }

                override fun onFailure(call: Call<WanResponse<String>>, t: Throwable) {
                    Log.d("OWEN", "login onFailure")
                }

            })
        }
    }
}