package cc.lixiaoyu.dslui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView {
            linearLayout(LinearLayoutCompat.VERTICAL) {
                text("owenleexiaoyu", 400, 200) {
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                }
                val textView = TextView(this.context)
                textView.text = "hello world"
                addView(textView)
                viewGroup<RelativeLayout> {
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_bright))
                    text("haha") {
                        layoutParams {
                            leftMargin = 400
                        }
                    }
                    image(R.mipmap.ic_launcher) {
                        val param = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                        layoutParams = param
                    }
                }
                view<Button> {
                    text = "A button"
                    setOnClickListener {
                        Toast.makeText(context, "click button", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}