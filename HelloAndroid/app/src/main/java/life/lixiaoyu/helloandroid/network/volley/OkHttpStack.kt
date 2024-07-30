package life.lixiaoyu.helloandroid.network.volley

import com.android.volley.Header
import com.android.volley.Request
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.HttpResponse
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.lang.IllegalArgumentException

class OkHttpStack: BaseHttpStack() {

    private val okHttpClient = OkHttpClient()

    override fun executeRequest(
        request: Request<*>?,
        additionalHeaders: MutableMap<String, String>?
    ): HttpResponse {
        if (request == null) throw IllegalArgumentException("Request is null")
        val url = request.url
        val headersBuilder = Headers.Builder()
        additionalHeaders?.let {
            it.forEach { entry ->
                headersBuilder.add(entry.key, entry.value)
            }
        }
        request.headers.forEach { entry ->
            headersBuilder.add(entry.key, entry.value)
        }
        val okRequestBuilder: okhttp3.Request.Builder = okhttp3.Request.Builder()
            .url(url)
            .headers(headersBuilder.build())
        buildMethodAndRequestBody(request, okRequestBuilder)
        val call = okHttpClient.newCall(okRequestBuilder.build())
        try {
            val okResponse: Response = call.execute()
            val headers = mutableListOf<Header>()
            okResponse.headers.forEach {
                headers.add(Header(it.first, it.second))
            }
            return HttpResponse(okResponse.code, headers, okResponse.body?.bytes())
        } catch(e: IOException) {
            throw e
        }
    }

    private fun buildMethodAndRequestBody(request: Request<*>, okRequestBuilder: okhttp3.Request.Builder) {
        when (request.method) {
            Request.Method.DEPRECATED_GET_OR_POST -> {
                if (request.body == null) {
                    okRequestBuilder.get()
                } else {
                    okRequestBuilder.post(request.body.toRequestBody())
                }
            }
            Request.Method.GET -> okRequestBuilder.get()
            Request.Method.POST -> okRequestBuilder.post(request.body.toRequestBody())
            Request.Method.DELETE -> okRequestBuilder.delete(request.body.toRequestBody())
            Request.Method.HEAD -> okRequestBuilder.head()
            Request.Method.PUT -> okRequestBuilder.put(request.body.toRequestBody())
            Request.Method.OPTIONS -> okRequestBuilder.method("OPTIONS", null)
            Request.Method.PATCH -> okRequestBuilder.patch(request.body.toRequestBody())
            Request.Method.TRACE -> okRequestBuilder.method("TRACE", null)
            else ->  throw IllegalArgumentException("Non supported http method: ${request.method}")
        }
    }
}