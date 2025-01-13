package life.lixiaoyu.helloandroid.network.okhttp

import okhttp3.Interceptor
import okhttp3.Response

class AddHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .addHeader("my_header", "abc")
            .build()
        return chain.proceed(request)
    }
}