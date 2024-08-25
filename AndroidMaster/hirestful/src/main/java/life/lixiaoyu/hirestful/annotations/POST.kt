package life.lixiaoyu.hirestful.annotations

/**
 * @POST("/cities/all")
 * fun test(@Field("province") provinceId: String)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST(val value: String, val formPost: Boolean = true)
