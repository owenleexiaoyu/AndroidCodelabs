package cc.lixiaoyu.coroutinetest

import okhttp3.OkHttpClient

object OkHttpManager {

    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient()
        }
        return okHttpClient!!
    }
}