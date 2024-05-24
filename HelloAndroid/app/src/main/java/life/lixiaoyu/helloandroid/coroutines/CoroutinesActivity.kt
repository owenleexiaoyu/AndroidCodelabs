package life.lixiaoyu.helloandroid.coroutines

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import life.lixiaoyu.helloandroid.R
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext

class CoroutinesActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("OWEN", "catch by  CoroutineExceptionHandler")
            throwable.printStackTrace()
        }
        val scope = CoroutineScope(Dispatchers.Main + Job() + CoroutineName("parent") + exceptionHandler)

//        scope.launch {
//            async {
//                Log.d("OWEN", "async execute")
//                throw IllegalStateException("async fail")
//            }
//        }
        val deferred = scope.async {
            Log.d("OWEN", "async execute")
            throw IllegalStateException("async fail")
        }
//        deferred.await()


//            scope.launch(Dispatchers.Default) {
//                delay(3000)
//                throw IllegalStateException("child fail")
//            }
//            scope.launch(Dispatchers.Default) {
//                repeat(10) {
//                    Log.d("OWEN", "job2 $it")
//                    delay(1000)
//                }
//            }
//            scope.launch(SupervisorJob()) {
//                launch(Dispatchers.Default) {
//                    delay(3000)
//                    throw IllegalStateException("child fail")
//                }
//                launch(Dispatchers.Default) {
//                    repeat(10) {
//                        Log.d("OWEN", "job2 $it")
//                        delay(1000)
//                    }
//                }
//
//            }

//        scope.launch(SupervisorJob()) {
//            throw IllegalStateException("child fail")
//            val startTime = System.currentTimeMillis()
//            val job1 = launch(Dispatchers.Default) {
//                Log.d("OWEN", "job1 execute")
//                var nextPrintTime = startTime
//                var i = 0
//                while (i < 5  && isActive) {
//                    if (System.currentTimeMillis() >= nextPrintTime) {
//                        Log.d("OWEN", "job1 Hello ${i++}")
//                        nextPrintTime += 500
//                    }
//                }
//                throw IllegalStateException("job1 fail")
//            }
//
//            val job2 = launch(Dispatchers.Default) {
//                Log.d("OWEN", "job2 execute")
//                repeat(15) {
//                    Log.d("OWEN", "job2 World ${it}")
//                    delay(500)
//                }
//            }
//            delay(5 * 1000)
//            Log.d("OWEN", "Cancel!")
//            cancel()
//            Log.d("OWEN", "Done!")
//        }
    }
}