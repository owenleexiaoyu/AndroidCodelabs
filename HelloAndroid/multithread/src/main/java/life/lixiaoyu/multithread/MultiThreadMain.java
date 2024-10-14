package life.lixiaoyu.multithread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadMain {

    public static void main(String[] args) {
//        thread();

//        runnable();

//        threadFactory();

//        executor();

//        callable();

//        TestDemo demo = new SynchronizedDemo1();
//        demo.runTest();

//        TestDemo demo = new SynchronizedDemo1_1();
//        demo.runTest();

//        TestDemo demo2 = new SynchronizedDemo2();
//        demo2.runTest();

//        TestDemo demo2 = new SynchronizedDemo2_1();
//        demo2.runTest();

//        TestDemo demo3 = new SynchronizedDemo3();
//        demo3.runTest();

//        TestDemo stopDemo = new ThreadStopDemo();
//        stopDemo.runTest();

//        TestDemo interruptDemo = new ThreadInterruptDemo();
//        interruptDemo.runTest();

//        TestDemo waitNotifyDemo = new WaitNotifyDemo();
//        waitNotifyDemo.runTest();

//        TestDemo joinDemo = new ThreadJoinDemo();
//        joinDemo.runTest();

//        TestDemo handlerThreadDemo = new HandlerThreadDemo();
//        handlerThreadDemo.runTest();

//        TestDemo threadLocalDemo = new ThreadLocalDemo();
//        threadLocalDemo.runTest();

//        TestDemo aatWaitNotifyDemo = new AATWaitNotifyDemo();
//        aatWaitNotifyDemo.runTest();

        TestDemo aatSleepDemo = new AATSleepDemo();
        aatSleepDemo.runTest();
    }

    /**
     * 创建一个新的 Thread 并启动
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start();
    }

    /**
     * 使用 Runnable 来创建线程中执行的工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 工厂方法来创建线程
     */
    static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(@NotNull Runnable runnable) {
                return new Thread(runnable, "Thread-" + count.incrementAndGet());
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started");
            }
        };
        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread2= factory.newThread(runnable);
        thread2.start();
    }

    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started");
            }
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        // 其他的线程池方法
        Executor executor1 = Executors.newSingleThreadExecutor();
        Executor executor2 = Executors.newFixedThreadPool(10);
        Executor executor3 = Executors.newScheduledThreadPool(10);
        Executor executor4 = Executors.newSingleThreadScheduledExecutor();
        Executor executor5 = new ThreadPoolExecutor(5, 100, 5, TimeUnit.MINUTES, new SynchronousQueue());
    }

    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
