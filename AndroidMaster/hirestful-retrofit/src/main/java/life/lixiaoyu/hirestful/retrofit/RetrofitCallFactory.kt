package life.lixiaoyu.hirestful.retrofit

import life.lixiaoyu.hirestful.HiCall
import life.lixiaoyu.hirestful.HiCallback
import life.lixiaoyu.hirestful.HiConvert
import life.lixiaoyu.hirestful.HiRequest
import life.lixiaoyu.hirestful.HiResponse
import okhttp3.FormBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCallFactory(retrofitClient: Retrofit? = null, baseUrl: String): HiCall.Factory {

    private val retrofit: Retrofit
    private val apiService: RetrofitApiService
    private val gsonConvert: HiConvert

    init {
        retrofit = retrofitClient ?: Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(RetrofitApiService::class.java)
        gsonConvert = GsonConvert()
    }

    override fun newCall(request: HiRequest): HiCall<*> {
        return RetrofitCall<Any>(request)
    }


    inner class RetrofitCall<T>(val request: HiRequest): HiCall<T> {

        override fun execute(): HiResponse<T> {
            val realCall = createRealCall(request)
            val retrofitResponse = realCall.execute()
            return parseResponse(retrofitResponse)
        }

        private fun parseResponse(retrofitResponse: Response<ResponseBody>): HiResponse<T> {
            val rawData: String = if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.string() ?: ""
            } else {
                retrofitResponse.errorBody()?.string() ?: ""
            }
            return gsonConvert.convert(rawData, request.returnType!!)
        }

        override fun enqueue(callback: HiCallback<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val hiResponse = parseResponse(response)
                    callback.onSuccess(hiResponse)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFailed(t)
                }

            })
        }

        private fun createRealCall(request: HiRequest): Call<ResponseBody> {
            val headers = request.headers ?: emptyMap()
            val params = request.parameters ?: emptyMap()

            return if (request.httpMethod == HiRequest.METHOD.GET) {
                apiService.doGet(headers, request.endPointUrl(), params)
            } else {
                val formBodyBuilder = FormBody.Builder()
                params.forEach { entry ->
                    formBodyBuilder.add(entry.key, entry.value)
                }
                apiService.doPost(headers, request.endPointUrl(), formBodyBuilder.build())
            }
        }

    }
}