package life.lixiaoyu.androidmaster.hirestful

import com.google.gson.annotations.SerializedName
import life.lixiaoyu.hirestful.HiCall
import life.lixiaoyu.hirestful.annotations.Field
import life.lixiaoyu.hirestful.annotations.GET
import life.lixiaoyu.hirestful.annotations.POST

interface WanAndroidApi {

    @GET("/banner/json")
    fun getBannerList(): HiCall<List<Banner>>

    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): HiCall<String>

}

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
