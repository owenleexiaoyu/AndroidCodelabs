package life.lixiaoyu.helloandroid.network.entity

import com.google.gson.annotations.SerializedName

data class WanResponse<T>(
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String,
    @SerializedName("data")
    val data: T?
) {
    fun isSuccess(): Boolean = errorCode == 0
}
