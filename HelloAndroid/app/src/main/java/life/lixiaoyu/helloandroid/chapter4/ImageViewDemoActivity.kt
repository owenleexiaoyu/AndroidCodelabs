package life.lixiaoyu.helloandroid.chapter4

import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import life.lixiaoyu.helloandroid.R

class ImageViewDemoActivity: AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var matrixImage: ImageView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageview_demo)
        image = findViewById(R.id.image)
        matrixImage = findViewById(R.id.matrix_image)
        matrixImage.scaleType = ImageView.ScaleType.MATRIX
        val matrix = Matrix().apply {
            setTranslate(100F, 100F) // x, y 各平移 100 px
            preRotate(30F) // 顺时针旋转 30 度
        }
        matrixImage.imageMatrix = matrix
        button = findViewById(R.id.button)
        button.setOnClickListener {
            image.setImageResource(R.drawable.banana)
        }
    }
}