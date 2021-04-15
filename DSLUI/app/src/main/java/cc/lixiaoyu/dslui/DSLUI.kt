package cc.lixiaoyu.dslui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat

/**
 * Author: lixiaoyu.owen
 * Time: 2021/4/12
 * Desc: 用 Kotlin DSL 语法构建一个简易的声明式 UI 框架
 * 博客来源：https://www.jianshu.com/p/f160d8f147c3
 */

/**
 * 给 Activity 增加一个扩展方法，传入一个Lambda，得到 View，调用原生的 setContentView
 */
inline fun Activity.contentView(block: Activity.() -> View) {
    setContentView(block())
}

fun Activity.linearLayout(
    @LinearLayoutCompat.OrientationMode orientation: Int = LinearLayoutCompat.HORIZONTAL,
    block: LinearLayoutCompat.() -> Unit
): View {
    return LinearLayoutCompat(this).apply {
        setOrientation(orientation)
        block()
    }
}

inline fun <reified T : ViewGroup> Activity.viewGroup(block: T.() -> Unit): View {
    val constructor = T::class.java.getConstructor(Context::class.java)
    return constructor.newInstance(this).apply {
        block()
    }
}

inline fun <reified T : ViewGroup> ViewGroup.viewGroup(block: T.() -> Unit) {
    val constructor = T::class.java.getConstructor(Context::class.java)
    (constructor.newInstance(this.context) as T).apply {
        block()
        this@viewGroup.addView(this)
    }
}


inline fun ViewGroup.text(
    text: CharSequence,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: TextView.() -> Unit
) {
    TextView(this.context).apply {
        this.text = text
        val param = ViewGroup.MarginLayoutParams(width, height)
        layoutParams = param
        block()
        addView(this)
    }
}

fun ViewGroup.image(
    @DrawableRes src: Int,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    block: ImageView.() -> Unit
) {
    ImageView(this.context).apply {
        setImageResource(src)
        val param = ViewGroup.MarginLayoutParams(width, height)
        layoutParams = param
        block()
        addView(this)
    }
}

inline fun <reified T : View> ViewGroup.view(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    noinline block: (T.() -> Unit)? = null
) {
    val constructor = T::class.java.getConstructor(Context::class.java)
    (constructor.newInstance(this.context) as T).apply {
        val param = ViewGroup.MarginLayoutParams(width, height)
        param.leftMargin = 100
        layoutParams = param
        block?.invoke(this)
        addView(this)
    }
}

fun View.setMargins(
    leftMargin: Int = 0,
    topMargin: Int = 0,
    rightMargin: Int = 0,
    bottomMargin: Int = 0
) {
    val params = layoutParams ?: ViewGroup.MarginLayoutParams(width, height)
    (params as ViewGroup.MarginLayoutParams).setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
    layoutParams = params
}

inline fun View.layoutParams(block: ViewGroup.MarginLayoutParams.() -> Unit) {
    val params = layoutParams ?: ViewGroup.MarginLayoutParams(width, height)
    (params as ViewGroup.MarginLayoutParams).block()
    layoutParams = params
}