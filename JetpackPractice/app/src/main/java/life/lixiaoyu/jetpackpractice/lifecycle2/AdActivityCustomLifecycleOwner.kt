package life.lixiaoyu.jetpackpractice.lifecycle2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import life.lixiaoyu.jetpackpractice.R

class AdActivityCustomLifecycleOwner : Activity(), LifecycleOwner {

    lateinit var tvAdTime: TextView
    lateinit var btnSkipAd: Button

    // 创建一个 LifecycleRegistry 对象
    private var lifecycleRegistry = LifecycleRegistry(this)

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
                this@AdActivityCustomLifecycleOwner.enterMainPage()
            }
        }
        lifecycle.addObserver(adManager)
    }

    private fun enterMainPage() {
        val intent = Intent(this, AppMainPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry  // 将 LifecycleRegistry 对象直接赋值给 lifecycle
}