package cc.lixiaoyu.loadimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import cc.lixiaoyu.loadimage.coil.CoilImageActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnCoil: Button
    private lateinit var btnGlide: Button
    private lateinit var btnFresco: Button
    private lateinit var btnPicasso: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCoil = findViewById(R.id.btnCoil)
        btnGlide = findViewById(R.id.btnGlide)
        btnFresco = findViewById(R.id.btnFresco)
        btnPicasso = findViewById(R.id.btnPicasso)

        btnCoil.setOnClickListener {
            startActivity(Intent(this@MainActivity, CoilImageActivity::class.java))
        }
        btnGlide.setOnClickListener {
            startActivity(Intent(this@MainActivity, CoilImageActivity::class.java))
        }
        btnFresco.setOnClickListener {
            startActivity(Intent(this@MainActivity, CoilImageActivity::class.java))
        }
        btnPicasso.setOnClickListener {
            startActivity(Intent(this@MainActivity, CoilImageActivity::class.java))
        }
    }
}