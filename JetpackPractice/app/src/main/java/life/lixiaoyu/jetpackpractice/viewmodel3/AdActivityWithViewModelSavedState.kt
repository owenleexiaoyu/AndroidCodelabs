package life.lixiaoyu.jetpackpractice.viewmodel3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R
import life.lixiaoyu.jetpackpractice.lifecycle2.AdManageListener
import life.lixiaoyu.jetpackpractice.lifecycle2.AdManagerWithLifecycle
import life.lixiaoyu.jetpackpractice.lifecycle2.AppMainPageActivity

class AdActivityWithViewModelSavedState : AppCompatActivity() {

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
        val adViewModel = ViewModelProvider(this).get(AdViewModelWithSavedState::class.java)
        val adManager = AdManagerWithLifecycle(adViewModel.millisInFuture)
        adManager.adListener = object : AdManageListener {

            override fun timing(second: Int) {
                tvAdTime.text = "广告剩余 $second 秒"
                adViewModel.millisInFuture = second.toLong() * 1000
            }

            override fun enterMainPage() {
                this@AdActivityWithViewModelSavedState.enterMainPage()
            }
        }
        lifecycle.addObserver(adManager)
    }

    private fun enterMainPage() {
        val intent = Intent(this, AppMainPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}