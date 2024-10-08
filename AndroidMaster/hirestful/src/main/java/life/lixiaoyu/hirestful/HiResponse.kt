package life.lixiaoyu.hirestful

open class HiResponse<T> {

    companion object {
        val SUCCESS: Int = 0
    }

    var rawData: String? = null                 // 原始数据
    var code = 0                                // 业务状态码
    var msg: String? = null                     // 错误信息
    var data: T? = null                         // 业务数据
    var errorData: Map<String, String>? = null  // 错误状态下的数据

}