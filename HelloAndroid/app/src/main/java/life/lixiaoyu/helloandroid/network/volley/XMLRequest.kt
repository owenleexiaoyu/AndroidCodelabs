package life.lixiaoyu.helloandroid.network.volley

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.HttpHeaderParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class XMLRequest(
    method: Int,
    url: String,
    private var listener: Response.Listener<XmlPullParser>?,
    errorListener: ErrorListener?
) : Request<XmlPullParser>(method, url, errorListener) {

    constructor(url: String, listener: Response.Listener<XmlPullParser>?, errorListener: ErrorListener? = null) : this(Method.GET, url, listener, errorListener)

    private val lock = Any()

    // 解析网络请求结果
    override fun parseNetworkResponse(response: NetworkResponse?): Response<XmlPullParser> {
        return try {
            val xmlString = String(
                response?.data ?: byteArrayOf(),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlString))
            Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: XmlPullParserException) {
            Response.error(ParseError(e))
        }
    }

    // 回调成功的请求
    override fun deliverResponse(response: XmlPullParser?) {
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