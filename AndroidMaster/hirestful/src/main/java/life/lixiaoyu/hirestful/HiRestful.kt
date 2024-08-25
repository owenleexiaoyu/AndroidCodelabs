package life.lixiaoyu.hirestful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

class HiRestful(
    val baseUrl: String,
    val callFactory: HiCall.Factory,
) {

    private val interceptors: MutableList<HiInterceptor> = mutableListOf()

    private val methodService: ConcurrentHashMap<Method, MethodParser> = ConcurrentHashMap()

    /**
     * 添加网络请求拦截器
     */
    fun addInterceptor(interceptor: HiInterceptor) {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            var methodParser = methodService[method]
            if (methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService[method] = methodParser
            }
            val request = methodParser.newRequest()
            val scheduler = Scheduler(callFactory, interceptors)
            scheduler.newCall(request)
        } as T
    }
}