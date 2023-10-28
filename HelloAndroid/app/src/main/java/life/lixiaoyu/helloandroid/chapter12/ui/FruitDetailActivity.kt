package life.lixiaoyu.helloandroid.chapter12.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import life.lixiaoyu.helloandroid.databinding.ActivityFruitDetailBinding

class FruitDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailBinding

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMG_ID = "fruit_img_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: ""
        val fruitImgId = intent.getIntExtra(FRUIT_IMG_ID, 0)
        setSupportActionBar(binding.detailToolbar)
        binding.collapsingToolbar.title = fruitName
        Glide.with(this).load(fruitImgId).into(binding.fruitDetailImg)
        binding.fruitDetailText.text = generateFruitContent(fruitName)
    }

    private fun generateFruitContent(fruitName: String): CharSequence? {
        return fruitName.repeat(50)
    }
}