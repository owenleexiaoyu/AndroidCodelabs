package life.lixiaoyu.hirestful

import java.lang.reflect.Type

class HiRequest {

    var httpMethod: Int = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null

    annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
        }
    }

    fun endPointUrl(): String {
        if (domainUrl == null || relativeUrl == null) {
            throw IllegalStateException("Relative url must not be null")
        }
        if (relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }
        val indexOf = domainUrl!!.indexOf("/")
        if (indexOf > 0) {
            return domainUrl!!.substring(0, indexOf) + relativeUrl
        }
        return domainUrl + relativeUrl
    }

    fun addHeader(name: String, value: String) {
        if (headers == null) {
            headers = mutableMapOf()
        }
        headers!![name] = value
    }
}