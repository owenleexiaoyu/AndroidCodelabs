package life.lixiaoyu.hirestful.okhttp

import android.os.Handler
import android.os.Looper
import life.lixiaoyu.hirestful.HiCall
import life.lixiaoyu.hirestful.HiCallback
import life.lixiaoyu.hirestful.HiConvert
import life.lixiaoyu.hirestful.HiRequest
import life.lixiaoyu.hirestful.HiResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.Executor

class OkHttpCallFactory(client: OkHttpClient? = null): HiCall.Factory {

    private val okHttpClient: OkHttpClient
    private val gsonConvert: HiConvert
    private val callbackExecutor: Executor

    init {
        okHttpClient = client ?: OkHttpClient()
        gsonConvert = GsonConvert()
        callbackExecutor = MainThreadCallbackExecutor()
    }

    override fun newCall(request: HiRequest): HiCall<*> {
        val okRequestBuilder = Request.Builder()
            .url(request.endPointUrl())
        if (request.httpMethod == 0) {
            okRequestBuilder.get()
        } else {
            val formBuilder = FormBody.Builder()
            request.parameters?.forEach { entry ->
                formBuilder.add(entry.key, entry.value)
            }
            val formBody = formBuilder.build()
            okRequestBuilder.post(formBody)
        }
        request.headers?.forEach {  entry ->
            okRequestBuilder.addHeader(entry.key, entry.value)
        }
        val okHttpCall = okHttpClient.newCall(okRequestBuilder.build())
        return OkHttpCall<String>(okHttpCall, request)
    }

    inner class OkHttpCall<T>(val rawCall: Call, val request: HiRequest): HiCall<T> {
        override fun execute(): HiResponse<T> {
            val okResponse = rawCall.execute()
            return okResponseToHiResponse(okResponse)
        }

        override fun enqueue(callback: HiCallback<T>) {
            rawCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callbackExecutor.execute {
                        callback.onFailed(e)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val hiResponse = okResponseToHiResponse(response)
                    callbackExecutor.execute {
                        callback.onSuccess(hiResponse)
                    }
                }
            })
        }

        fun okResponseToHiResponse(okResponse: Response): HiResponse<T> {
            val rawData: String = if (okResponse.isSuccessful) {
                okResponse.body?.string() ?: ""
            } else {
                ""
            }
            return gsonConvert.convert(rawData, request.returnType!!)
        }

    }

    class MainThreadCallbackExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            if (command == null) return
            mainThreadHandler.post(command)
        }

    }
}