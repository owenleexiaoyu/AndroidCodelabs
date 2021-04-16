package cc.lixiaoyu.coroutinetest

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.coroutines.*
import okhttp3.Request
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var pieceImageView1: ImageView
    private lateinit var pieceImageView2: ImageView

    @SuppressLint("CI_ByteDanceKotlinRules_Not_Allow_findViewById_Invoked_In_UI")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        pieceImageView1 = findViewById(R.id.pieceImageView1)
        pieceImageView2 = findViewById(R.id.pieceImageView2)
        loadImageFromNet(IMAGE_URL)
    }

    private fun loadImageFromNet(imageUrl: String) {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val image = getImage(imageUrl)
            val pieceImage1 = async { cutImageBitmap(image, 2) }
            val pieceImage2 = async { cutImageBitmap(image, 3) }
            progressBar.visibility = View.GONE
            showPieceImages(pieceImage1.await(), pieceImage2.await())
            imageView.setImageBitmap(image)
        }
    }

    private fun showPieceImages(pieceImage1: Bitmap, pieceImage2: Bitmap) {
        pieceImageView1.setImageBitmap(pieceImage1)
        pieceImageView2.setImageBitmap(pieceImage2)
    }

    /**
     * 从网络上获取一张图片
     */
    private suspend fun getImage(imageUrl: String): Bitmap = withContext(Dispatchers.IO) {
        val request = Request.Builder().get().url(imageUrl).build()
        val response = OkHttpManager.getOkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val bytes = response.body?.bytes() ?: byteArrayOf()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            throw IllegalStateException("network error")
        }
    }

    /**
     * 将一张图片
     */
    private suspend fun cutImageBitmap(src: Bitmap, rate: Int): Bitmap =
        withContext(Dispatchers.IO) {
            if (rate <= 0) {
                src
            } else {
                val pieceWidth: Int = src.width / rate
                val pieceHeight: Int = src.height / rate
                val result = Bitmap.createBitmap(src, 0, 0, pieceWidth, pieceHeight)
                result
            }
        }

    companion object {
        const val IMAGE_URL = "http://image.wufazhuce.com/Fh02w8RBwtoPWR_UDw7rSXGZpS7I"
    }

}