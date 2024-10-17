package life.lixiaoyu.hiexecutor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.BlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * 支持按任务的优先级来执行
 * 支持线程池暂停，恢复（批量文件下载，上传）
 * 异步任务结果主动回调主线程
 * TODO 线程池能力监控，耗时任务检测，定时，延迟
 */
object HiExecutor {
    private const val TAG = "HiExecutor"
    private val hiExecutor: ThreadPoolExecutor

    private val lock: ReentrantLock = ReentrantLock()
    private val pauseCondition: Condition = lock.newCondition()

    private var isPaused = false

    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            // hi-executor-0
            thread.name = "hi-executor-${seq.getAndIncrement()}"
            return@ThreadFactory thread
        }
        hiExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    try {
                        lock.lock()
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                // 监控线程池耗时任务，线程创建数量，正在运行的数量
                Log.d(TAG, "已执行完的任务的优先级：${(r as PriorityRunnable).priority}")
            }
        }

    }

    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int  = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun <T> execute(@IntRange(from = 0, to = 10) priority: Int  = 0, callable: Callable<T>) {
        hiExecutor.execute(PriorityRunnable(priority, callable))
    }

    @Synchronized
    fun pause() {
        isPaused = true
        Log.d(TAG, "HiExecutor is paused")
    }

    @Synchronized
    fun resume() {
        isPaused = false
        try {
            lock.lock()
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.d(TAG, "HiExecutor is resumed")
    }

    class PriorityRunnable(val priority: Int, private val runnable: Runnable)
        : Runnable, Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }

    }

    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post { onPrepare() }
            val result: T = onBackground()
            mainHandler.post { onCompleted(result) }
        }
        open fun onPrepare() {

        }

        abstract fun onBackground(): T

        abstract fun onCompleted(t: T)
    }
}