package life.lixiaoyu.hirestful.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import life.lixiaoyu.hirestful.HiCall
import life.lixiaoyu.hirestful.HiCallback
import life.lixiaoyu.hirestful.HiConvert
import life.lixiaoyu.hirestful.HiRequest
import life.lixiaoyu.hirestful.HiResponse
import java.io.IOException

class VolleyCallFactory(context: Context): HiCall.Factory {

    private val requestQueue = Volley.newRequestQueue(context)
    private val gsonConvert: HiConvert = GsonConvert()

    override fun newCall(request: HiRequest): HiCall<*> {
        return VolleyCall<Any>(request)
    }

    inner class VolleyCall<T>(val request: HiRequest): HiCall<T> {

        private val lock: Object = Object()

        override fun execute(): HiResponse<T> {
            var hiResponse: HiResponse<T>? = null
            val stringRequest = object: StringRequest(
                if (request.httpMethod == HiRequest.METHOD.GET) Request.Method.GET else Request.Method.POST,
                request.endPointUrl(),
                {
                    val response = parseResponse(it)
                    synchronized(lock) {
                        hiResponse = response
                        lock.notifyAll()
                    }
                },
                {
                    val errorResponse = HiResponse<T>()
                    errorResponse.code = -1
                    errorResponse.msg = it.message
                    synchronized(lock) {
                        hiResponse = errorResponse
                        lock.notifyAll()
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return request.headers?.toMutableMap() ?: mutableMapOf()
                }
            }
            requestQueue.add(stringRequest)
            synchronized(lock) {
                lock.wait()
            }
            return hiResponse!!
        }

        override fun enqueue(callback: HiCallback<T>) {
            val stringRequest = object: StringRequest(
                if (request.httpMethod == HiRequest.METHOD.GET) Request.Method.GET else Request.Method.POST,
                request.endPointUrl(),
                {
                    val hiResponse = parseResponse(it)
                    callback.onSuccess(hiResponse)
                },
                {
                    callback.onFailed(it.cause ?: IOException("Unknown exception"))
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return request.headers?.toMutableMap() ?: mutableMapOf()
                }
            }
            requestQueue.add(stringRequest)
        }

        private fun parseResponse(response: String): HiResponse<T> {
            return gsonConvert.convert(response, request.returnType!!)
        }

    }
}