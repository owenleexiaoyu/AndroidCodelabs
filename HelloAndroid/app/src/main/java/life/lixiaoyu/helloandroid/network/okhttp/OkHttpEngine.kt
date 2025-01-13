package life.lixiaoyu.helloandroid.network.okhttp

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class OkHttpEngine(context: Context) {

    private val client: OkHttpClient
    private val uiHandler: Handler
    private val gson: Gson
    init {
        val sdCache = context.externalCacheDir
        val cacheSize: Long = 10 * 1024 * 1024
        client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .cache(Cache(sdCache!!.absoluteFile, cacheSize))
            .build()
        uiHandler = Handler(Looper.getMainLooper())
        gson = Gson()
    }

    fun <M> getAsync(url: String, callback: HttpCallback<M>? = null) {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                sendFailCallback(call.request(), e, callback)
            }

            override fun onResponse(call: Call, response: Response) {
                sendSuccessCallback(response, callback)
            }

        })
    }

    private fun <M> sendSuccessCallback(response: Response, callback: HttpCallback<M>?) {
        val responseData = response.body?.string() ?: ""
        val model = gson.fromJson<M>(responseData, callback?.type)
        callback?.let {
            uiHandler.post {
                try {
                    it.onSuccess(model, response)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun sendFailCallback(request: Request, exception: Exception, callback: HttpCallback<*>?) {
        callback?.let {
            uiHandler.post {
                try {
                    it.onFail(request, exception)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    companion object {
        private var instance: OkHttpEngine? = null
        fun get(context: Context): OkHttpEngine {
            if (instance == null) {
                synchronized(this::class.java) {
                    if (instance == null) {
                        instance = OkHttpEngine(context)
                    }
                }
            }
            return instance!!
        }
    }
}

abstract class HttpCallback<M> {

    val type: Type?

    init {
        val superClass = javaClass.genericSuperclass
        val types = (superClass as? ParameterizedType)?.actualTypeArguments
        type = if (!types.isNullOrEmpty()) {
            types[0]
        } else null
    }


    /**
     * 请求成功时回调
     * @param result 反序列化后的 Model 数据
     * @param rawResponse 原始的 Response 数据
     */
    open fun onSuccess(result: M, rawResponse: Response) {}

    /**
     * 请求失败时回调
     *
     */
    open fun onFail(request: Request, exception: Exception) {}
}
