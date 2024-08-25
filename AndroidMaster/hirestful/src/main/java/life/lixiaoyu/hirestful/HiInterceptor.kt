package life.lixiaoyu.hirestful

interface HiInterceptor {

    fun intercept(chain: Chain): Boolean

    /**
     *
     */
    interface Chain {

        /**
         * 用于判断是否是在网络请求发起阶段
         */
        val isRequestPeriod: Boolean get() = false

        fun request(): HiRequest

        /**
         * 这个 response 对象在网络请求发起之前，是为空的
         */
        fun response(): HiResponse<*>?
    }
}