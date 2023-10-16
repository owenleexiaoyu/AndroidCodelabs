package life.lixiaoyu.androidfirstlineofcode.network.entity.retrofit

import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Observable
import life.lixiaoyu.androidfirstlineofcode.network.entity.Banner
import life.lixiaoyu.androidfirstlineofcode.network.entity.WanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WanAndroidApi {

    @GET("/banner/json")
    fun getBannerList(): Call<WanResponse<List<Banner>>>

    fun getBannerListObservable(): Observable<List<Banner>>

    @POST("/user/register")
    fun register(@Body request: RegisterRequest): Call<WanResponse<String>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<WanResponse<String>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @FieldMap request: Map<String, String>
    ): Call<WanResponse<String>>
}

data class RegisterRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("repassword")
    val rePassword: String
)