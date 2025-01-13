package life.lixiaoyu.helloandroid.network.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.d("LogInterceptor", "Sending request, url: ${request.url}, connection: ${chain.connection()}, headers:\n${request.headers.toList()}")
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.d("LogInterceptor", "Received response, url: ${response.request.url}, length: ${(t2 - t1) / 1e6 }ms, http status code: ${response.code}, headers:\n${response.headers.toList()}")
        return response
    }
}