package life.lixiaoyu.hirestful.annotations

/**
 * @GET("/cities/{provinceId}")
 * fun test(@Path("provinceId") provinceId: String)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value: String)
