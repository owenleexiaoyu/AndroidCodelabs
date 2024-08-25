package life.lixiaoyu.hirestful

/**
 * 网络请求的回调
 */
interface HiCallback<T> {
    /**
     * 请求成功的回调
     * @param response 请求结果
     */
    fun onSuccess(response: HiResponse<T>)

    /**
     * 请求失败的回调
     * @param throwable 发生的异常
     */
    fun onFailed(throwable: Throwable)
}