package life.lixiaoyu.hirestful.annotations

/**
 * @BaseUrl("https://api.example.com/")
 * fun test(@Field("province") provinceId: String)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Field(val value: String)
