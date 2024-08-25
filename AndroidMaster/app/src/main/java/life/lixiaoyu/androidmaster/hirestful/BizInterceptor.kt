package life.lixiaoyu.androidmaster.hirestful

import android.util.Log
import life.lixiaoyu.hirestful.HiInterceptor

class BizInterceptor: HiInterceptor {

    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val request = chain.request()
            request.addHeader("auth-token", "12345")
        } else {
            Log.d("BizInterceptor", "Response code = ${chain.response()?.code}" )
        }
        return false
    }
}