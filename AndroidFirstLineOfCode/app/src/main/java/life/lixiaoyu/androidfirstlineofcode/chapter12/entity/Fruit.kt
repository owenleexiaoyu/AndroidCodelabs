package life.lixiaoyu.androidfirstlineofcode.chapter12.entity

import androidx.annotation.DrawableRes

data class Fruit(
    val name: String,
    @DrawableRes val resId: Int
)