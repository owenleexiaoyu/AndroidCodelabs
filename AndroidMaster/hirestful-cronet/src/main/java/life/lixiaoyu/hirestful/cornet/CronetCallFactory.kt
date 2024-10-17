package life.lixiaoyu.hirestful.cornet

import android.content.Context
import android.util.Log
import life.lixiaoyu.hirestful.HiCall
import life.lixiaoyu.hirestful.HiCallback
import life.lixiaoyu.hirestful.HiRequest
import life.lixiaoyu.hirestful.HiResponse
import org.chromium.net.CronetEngine
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import org.chromium.net.apihelpers.UploadDataProviders
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

//class CronetCallFactory(context: Context)  : HiCall.Factory {
//
//    private val cronetEngine = CronetEngine.Builder(context)
//        .enableHttp2(true)
//        .enableQuic(true)
//        .setUserAgent("Hirestful-Cronet")
//        .build()
//
//    private val executor: Executor = Executors.newSingleThreadExecutor()
//
//    private val callback: UrlRequest.Callback = MyUrlRequestCallback()
//
//    override fun newCall(request: HiRequest): HiCall<*> {
//        return CronetCall<Any>(request)
//    }
//
//    inner class CronetCall<T>(val request: HiRequest): HiCall<T> {
//
//        override fun execute(): HiResponse<T> {
//            val cronetRequest = buildCronetRequest(request)
//            cronetRequest.start()
//            return HiResponse()
//        }
//
//        override fun enqueue(callback: HiCallback<T>) {
//            val cronetRequest = buildCronetRequest(request)
//            cronetRequest.start()
//        }
//
//        private fun buildCronetRequest(hiRequest: HiRequest): UrlRequest {
//            val requestBuilder = cronetEngine.newUrlRequestBuilder(
//                hiRequest.endPointUrl(),
//                MyUrlRequestCallback(),
//                executor
//            )
//            if (hiRequest.httpMethod == HiRequest.METHOD.GET) {
//                requestBuilder.setHttpMethod("GET")
//            } else {
//                requestBuilder.setHttpMethod("POST")
//                val formBodyStringBuilder = StringBuilder()
//                hiRequest.parameters?.forEach {
//                    formBodyStringBuilder.append("${it.key}=${it.value}&")
//                }
//                // 删除最后一个 & 符号
//                if (formBodyStringBuilder.isNotEmpty()) {
//                    formBodyStringBuilder.delete(formBodyStringBuilder.lastIndex, formBodyStringBuilder.lastIndex + 1)
//                }
//
//                val uploadDataProvider = UploadDataProviders.create(formBodyStringBuilder.toString().toByteArray())
//                requestBuilder.setUploadDataProvider(uploadDataProvider, executor)
//            }
//            hiRequest.headers?.forEach { header ->
//                requestBuilder.addHeader(header.key, header.value)
//            }
//            return requestBuilder.build()
//        }
//    }
//
//    class MyUrlRequestCallback: UrlRequest.Callback() {
//
//        private val TAG = "MyUrlRequestCallback"
//
//        override fun onRedirectReceived(
//            request: UrlRequest?,
//            info: UrlResponseInfo?,
//            newLocationUrl: String?
//        ) {
//            Log.i(TAG, "onRedirectReceived method called.")
//            // You should call the request.followRedirect() method to continue
//            // processing the request.
//            request?.followRedirect()
//        }
//
//        override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
//            Log.i(TAG, "onResponseStarted method called.")
//            // You should call the request.read() method before the request can be
//            // further processed. The following instruction provides a ByteBuffer object
//            // with a capacity of 102400 bytes for the read() method. The same buffer
//            // with data is passed to the onReadCompleted() method.
//            request?.read(ByteBuffer.allocateDirect(102400))
//        }
//
//        override fun onReadCompleted(
//            request: UrlRequest?,
//            info: UrlResponseInfo?,
//            byteBuffer: ByteBuffer?
//        ) {
//            Log.i(TAG, "onReadCompleted method called.")
//            // You should keep reading the request until there's no more data.
//            byteBuffer?.clear()
//            request?.read(byteBuffer)
//        }
//
//        override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
//            Log.i(TAG, "onSucceeded method called.")
//        }
//
//        override fun onFailed(
//            request: UrlRequest?,
//            info: UrlResponseInfo?,
//            error: CronetException?
//        ) {
//            Log.i(TAG, "onFailed method called.")
//        }
//
//    }
//}