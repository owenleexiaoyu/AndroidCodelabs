package life.lixiaoyu.jetpackpractice.livedata2

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

class AdActivityWithLiveData : AppCompatActivity() {

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
        val adViewModel = ViewModelProvider(this).get(AdViewModelWithLiveData::class.java)
        adViewModel.getTimingResult().observe(this) {
            tvAdTime.text = "广告剩余 $it 秒"
            if (it == 0) {
                enterMainPage()
            }
        }
        val adManager = AdManagerWithLiveData(adViewModel)
        lifecycle.addObserver(adManager)
    }

    private fun enterMainPage() {
        val intent = Intent(this, AppMainPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}