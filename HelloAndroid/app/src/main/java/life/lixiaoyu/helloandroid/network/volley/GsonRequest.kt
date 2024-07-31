package life.lixiaoyu.helloandroid.network.volley

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.Response.Listener
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class GsonRequest<T>(
    method: Int,
    url: String,
    private val clazz: Class<T>,
    private var listener: Listener<T>?,
    errorListener: ErrorListener?
) : Request<T>(method, url, errorListener) {

    private val gson = Gson()
    private val lock = Any()

    constructor(url: String, clazz: Class<T>, listener: Listener<T>?, errorListener: ErrorListener? = null) : this(
        Method.GET,
        url,
        clazz,
        listener,
        errorListener
    )

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val jsonString = String(
                response?.data ?: byteArrayOf(),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(jsonString, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: T) {
        synchronized(lock) {
            listener?.onResponse(response)
        }
    }

    // 取消请求
    override fun cancel() {
        super.cancel()
        synchronized(lock) {
            listener = null
        }
    }
}