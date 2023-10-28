package life.lixiaoyu.helloandroid.chapter3

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class BrowserActivity: AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        webView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        val uri = intent.data
        if (uri == null) {
            finish()
        }
        Log.d("BrowserActivity", "url: ${uri.toString()}")

        webView.loadUrl(uri.toString())
    }
}