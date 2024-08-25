package life.lixiaoyu.hirestful.annotations

/**
 * @Headers({"connection:keep-alive", "auth-token:token"})
 * @GET("/cities/all")
 * fun test(@Field("province") provinceId: String)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Headers(vararg val value: String)
