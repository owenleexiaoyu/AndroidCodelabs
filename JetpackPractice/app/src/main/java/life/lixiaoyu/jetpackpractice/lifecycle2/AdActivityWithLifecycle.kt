package life.lixiaoyu.jetpackpractice.lifecycle2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.jetpackpractice.R

class AdActivityWithLifecycle : AppCompatActivity() {

    lateinit var tvAdTime: TextView
    lateinit var btnSkipAd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_manage)
        tvAdTime = findViewById(R.id.tv_ad_time)
        btnSkipAd = findViewById(R.id.btn_skip_ad)
        btnSkipAd.setOnClickListener {
            enterMainPage()
        }

        val adManager = AdManagerWithLifecycle()
        adManager.adListener = object : AdManageListener {

            override fun timing(second: Int) {
                tvAdTime.text = "广告剩余 $second 秒"
            }

            override fun enterMainPage() {
                this@AdActivityWithLifecycle.enterMainPage()
            }
        }
        lifecycle.addObserver(adManager)

    }

    private fun enterMainPage() {
        val intent = Intent(this@AdActivityWithLifecycle, AppMainPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}