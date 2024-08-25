package life.lixiaoyu.hirestful.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitApiService {

    @GET
    fun doGet(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @QueryMap(encoded = true) queryMap: Map<String, String>
    ): Call<ResponseBody>

    @POST
    fun doPost(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

}