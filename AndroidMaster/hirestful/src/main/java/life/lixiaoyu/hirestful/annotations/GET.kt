package life.lixiaoyu.hirestful.annotations

/**
 * @GET("/cities/all")
 * fun test(@Field("province") provinceId: String)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val value: String)
