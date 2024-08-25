package life.lixiaoyu.hirestful.annotations

/**
 * @BaseUrl("https://api.example.com/")
 * fun test(@Field("province") provinceId: String)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val value: String)
