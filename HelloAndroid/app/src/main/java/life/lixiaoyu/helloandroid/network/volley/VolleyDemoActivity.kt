package life.lixiaoyu.helloandroid.network.volley

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageLoader.ImageCache
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import life.lixiaoyu.helloandroid.R
import org.json.JSONObject

class VolleyDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley)
        val tvBannerList = findViewById<TextView>(R.id.banner_list)
        val btnGetBanner = findViewById<Button>(R.id.get_banner_info)
        val queue = Volley.newRequestQueue(this, OkHttpStack())
        btnGetBanner.setOnClickListener {
            val url = "https://wanandroid.com/banner/json"
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    tvBannerList.text = response
                }
            ) {
                tvBannerList.text = "Request Error"
            }
            queue.add(stringRequest)
        }

        // 登录
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val url = "https://wanandroid.com/user/login"
            val loginRequest = object : StringRequest(
                Request.Method.POST,
                url,
                {
                    val resultJson = JSONObject(it)
                    val errorCode = resultJson.optInt("errorCode")
                    if (errorCode == 0) {
                        Toast.makeText(this@VolleyDemoActivity, "登录成功", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val errorMsg = resultJson.optString("errorMsg") ?: "业务失败"
                        Toast.makeText(
                            this@VolleyDemoActivity,
                            "登录失败，$errorMsg",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                {
                    Toast.makeText(this@VolleyDemoActivity, "登录失败，请求失败", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return mutableMapOf(
                        "username" to "aaaaaaa",
                        "password" to "bbbbbbb",
                    )
                }
            }
            queue.add(loginRequest)
        }

        // 使用 JsonObjectRequest 实现登录
        val btnLogin2 = findViewById<Button>(R.id.btn_login_2)
        btnLogin2.setOnClickListener {
            val url = "https://wanandroid.com/user/login"
            // 构造参数
            val params = JSONObject().apply {
                put("username", "aaaaaa")
                put("password", "bbbbbb")
            }
            val loginRequest = JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                { resultJson ->
                    val errorCode = resultJson.optInt("errorCode")
                    if (errorCode == 0) {
                        Toast.makeText(this@VolleyDemoActivity, "登录成功", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val errorMsg = resultJson.optString("errorMsg") ?: "业务失败"
                        Toast.makeText(
                            this@VolleyDemoActivity,
                            "登录失败，$errorMsg",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                {
                    Toast.makeText(this@VolleyDemoActivity, "登录失败，请求失败", Toast.LENGTH_SHORT)
                        .show()
                }
            )
            queue.add(loginRequest)
        }

        // 使用 ImageRequest 加载网络图片
        val btnLoadImage = findViewById<Button>(R.id.btn_load_image)
        val image = findViewById<ImageView>(R.id.image)
        val imageUrl = "http://image.wufazhuce.com/FiBzmwDotMhGiJjSuqi1Lg5h_Zjm"
        btnLoadImage.setOnClickListener {

            val imageRequest = ImageRequest(imageUrl,
                {
                    image.setImageBitmap(it)
                }, 0, 0, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,
                {
                    Toast.makeText(this@VolleyDemoActivity, "获取图片失败", Toast.LENGTH_SHORT).show()
                })
            queue.add(imageRequest)
        }

        // 使用 ImageLoader 加载网络图片
        val btnLoadImage2 = findViewById<Button>(R.id.btn_load_image2)

        btnLoadImage2.setOnClickListener {
            val imageLoader = ImageLoader(queue, object: ImageCache {
                override fun getBitmap(url: String?): Bitmap? {
                    return null
                }

                override fun putBitmap(url: String?, bitmap: Bitmap?) {

                }
            })
            val imageListener = ImageLoader.getImageListener(image, R.drawable.apple, R.drawable.banana)
            imageLoader.get(imageUrl, imageListener, 200, 200)
        }

        // 使用 NetworkImageView 加载网络图片
        val btnLoadImage3 = findViewById<Button>(R.id.btn_load_image3)
        val networkImage = findViewById<NetworkImageView>(R.id.network_image)
        networkImage.setDefaultImageResId(R.drawable.apple)
        networkImage.setErrorImageResId(R.drawable.banana)
        btnLoadImage3.setOnClickListener {
            val imageLoader = ImageLoader(queue, BitmapCache())
            networkImage.setImageUrl(imageUrl, imageLoader)
        }
    }
}