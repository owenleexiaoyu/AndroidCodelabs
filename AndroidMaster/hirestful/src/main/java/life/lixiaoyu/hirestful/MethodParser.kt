package life.lixiaoyu.hirestful

import life.lixiaoyu.hirestful.annotations.BaseUrl
import life.lixiaoyu.hirestful.annotations.Field
import life.lixiaoyu.hirestful.annotations.GET
import life.lixiaoyu.hirestful.annotations.Headers
import life.lixiaoyu.hirestful.annotations.POST
import life.lixiaoyu.hirestful.annotations.Path
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 解析如下格式的接口方法：
 * interface ApiService {
 *      @Headers("auth-token: token", "connection: keep-alive")
 *      @BaseUrl("http://api.example.com/a/")
 *      @POST("/cities/{province}")
 *      @GET("/cities")
 *      fun listCities(@Path("province") province: Int, @Field("page") page: Int): HiCall<JsonObject>
 * }
 */

class MethodParser(val baseUrl: String, method: Method, args: Array<Any>?) {


    private var domainUrl: String = ""
    private var returnType: Type? = null
    private var relativeUrl: String = ""
    private var httpMethod: Int = 0
    private var formPost: Boolean = true
    private val headers: MutableMap<String, String> = mutableMapOf()
    private val parameters: MutableMap<String, String> = mutableMapOf()

    init {
        // 解析方法上的注解，比如 @GET，@POST，@BaseUrl，@Headers
        parseMethodAnnotations(method)

        // 解析方法里的参数和参数上的注解
        parseMethodParameters(method, args)

        // 解析方法里的返回值类型
        parseMethodReturnType(method)
    }

    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException("Method ${method.name} return type must be HiCall.class")
        }
        // 获取泛型返回类型
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) { "Method ${method.name} can only has one generic return type" }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalStateException("Method ${method.name} must have one generic return type")
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>?) {
        if (args == null) return
        require(method.parameterAnnotations.size == args.size) {
            "arguments annotations count(${method.parameterAnnotations.size}) dont match args count(${args.size})"
        }
        for (index in args.indices) {
            val annotations = method.parameterAnnotations[index]
            // 只允许每个参数有一个注解
            require(annotations.size <= 1) { "Field can only has one annotation: index = $index" }
            val value = args[index]
            require(isPrimitive(value)) { "8 basic types are supported for now, index = $index" }
            val annotation = annotations[index]
            if (annotation is Field) {
                val key = annotation.value
                parameters[key] = value.toString()
            } else if (annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if (replaceName.isNotEmpty() && replacement.isNotEmpty()) {
                    val newRelativeUrl = relativeUrl.replace("{$replaceName}", replacement)
                    relativeUrl = newRelativeUrl
                }
            } else {
                throw IllegalStateException("Cannot handle param annotation: ${annotation.javaClass.name}")
            }
        }

    }

    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java)
        try {
            // int, byte, short, long, boolean, char, double, float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    private fun parseMethodAnnotations(method: Method) {
        // 获取方法上的所有注解
        val annotations = method.annotations
        // 遍历所有的注解
        for (annotation in annotations) {
            when (annotation) {
                is GET -> {
                    relativeUrl = annotation.value
                    httpMethod = 0 // GET
                }

                is POST -> {
                    relativeUrl = annotation.value
                    httpMethod = 1 // POST
                    formPost = annotation.formPost
                }

                is Headers -> {
                    val headerArray = annotation.value
                    for (header in headerArray) {
                        val colonIndex = header.indexOf(":")
                        require(!(colonIndex == 0 || colonIndex == -1)) { "@Headers value must be in the form of [name:value], but found [$header]" }
                        val name = header.substring(0, colonIndex)
                        val value = header.substring(colonIndex + 1).trim()
                        headers[name] = value
                    }
                }

                is BaseUrl -> {
                    domainUrl = annotation.value
                }

                else -> {
                    throw IllegalStateException("Cannot handle method annotation: ${annotation.javaClass.name}")
                }
            }
        }
        require(httpMethod == 0 || httpMethod == 1) { "Method ${method.name} must has one of @GET, @POST annotation" }
        if (domainUrl.isEmpty()) {
            domainUrl = baseUrl
        }
    }

    fun newRequest(): HiRequest {
        val request = HiRequest()
        request.domainUrl = domainUrl
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.parameters = parameters
        request.headers = headers
        request.httpMethod = httpMethod
        return request
    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>?): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}