package life.lixiaoyu.hirestful

import java.io.IOException
import kotlin.jvm.Throws

/**
 * 表示一次网络请求
 */
interface HiCall<T> {

    /**
     * 同步网络请求
     */
    @Throws(IOException::class)
    fun execute(): HiResponse<T>

    /**
     * 异步网络请求
     */
    fun enqueue(callback: HiCallback<T>)

    /**
     * 创建 HiCall 的 Factory
     */
    interface Factory {
        /**
         * 创建一个 HiCall 对象
         */
        fun newCall(request: HiRequest): HiCall<*>
    }

}