package life.lixiaoyu.jetpackpractice.viewmodel2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R
import life.lixiaoyu.jetpackpractice.lifecycle2.AdManageListener
import life.lixiaoyu.jetpackpractice.lifecycle2.AdManagerWithLifecycle
import life.lixiaoyu.jetpackpractice.lifecycle2.AppMainPageActivity

class AdActivityWithoutViewModel : AppCompatActivity() {

    lateinit var tvAdTime: TextView
    lateinit var btnSkipAd: Button

    // 计时时长，默认是 5s
    var millisInFuture: Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_manage)
        tvAdTime = findViewById(R.id.tv_ad_time)
        btnSkipAd = findViewById(R.id.btn_skip_ad)
        btnSkipAd.setOnClickListener {
            enterMainPage()
        }
        savedInstanceState?.let {
            millisInFuture = it.getLong(KEY_MILLIS_IN_FUTURE)
        }
        val adManager = AdManagerWithLifecycle(millisInFuture)
        adManager.adListener = object : AdManageListener {

            override fun timing(second: Int) {
                tvAdTime.text = "广告剩余 $second 秒"
                millisInFuture = second.toLong() * 1000
            }

            override fun enterMainPage() {
                this@AdActivityWithoutViewModel.enterMainPage()
            }
        }
        lifecycle.addObserver(adManager)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_MILLIS_IN_FUTURE, millisInFuture)
    }

    private fun enterMainPage() {
        val intent = Intent(this, AppMainPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val KEY_MILLIS_IN_FUTURE = "key_millis_in_future"
    }
}