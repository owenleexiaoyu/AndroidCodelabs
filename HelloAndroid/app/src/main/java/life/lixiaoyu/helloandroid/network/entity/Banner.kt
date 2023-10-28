package life.lixiaoyu.helloandroid.network.entity

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("imagePath")
    val imagePath: String = ""
)