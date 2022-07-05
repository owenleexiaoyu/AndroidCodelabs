package cc.lixiaoyu.coroutine_kotlin

import kotlinx.coroutines.*

/**
 * 测试 cancellation
 */
//fun main() = runBlocking {
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        while (i < 5) {
//            yield()
//            // print s message twice a second
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("Hello ${i++}")
//                nextPrintTime += 500L
//            }
//        }
//    }
//    delay(1000L)
//    println("Cancel!")
//    job.cancel()
//    println("Done!")
//}

/**
 * 测试 调用 deferred.cancel 之后调用 await
 */
//fun main() = runBlocking {
//    val deferred = async {
//        println("hello")
//    }
//    println("Cancel")
//    deferred.cancel()
//    deferred.await()
//}

/**
 * cancel 时候可以通过try-catch-finally，在finally中做清理
 */
//fun main() = runBlocking {
//    val job = launch {
//        try {
//            println("work")
//        } catch (e: CancellationException) {
//            println("work cancelled")
//        } finally {
//            println("clean up")
//        }
//    }
//    delay(100L)
//    println("cancel")
//    job.cancel()
//    println("done")
//}

/**
 * 测试 cancel：finally 中有 suspend
 */
//fun main() = runBlocking {
//    val job = launch {
//        try {
//            println("work")
//        } catch (e: CancellationException) {
//            println("work cancelled")
//        } finally {
//            withContext(Dispatchers.IO) {
//                delay(1000L)
//                println("clean up done")
//            }
//        }
//    }
//    delay(1000L)
//    println("cancel")
//    job.cancel()
//    println("done")
//}

fun test()  {
//    launch {
    val scope = CoroutineScope(Job())
    val deferred = scope.async {
            println("async task")
            throw IllegalAccessException("test exception")
        }
    deferred.await()
//    }
}

fun main() {
    test()
}