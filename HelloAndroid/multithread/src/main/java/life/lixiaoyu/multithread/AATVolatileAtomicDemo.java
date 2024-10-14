package life.lixiaoyu.multithread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比较 volatile 关键字和 Atomic 原子类的作用
 */
public class AATVolatileAtomicDemo {

    public static void main(String[] args) {
        final Task task = new Task();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    task.incrementAtomic();
                    task.incrementVolatile();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
        System.out.println("atomicCount 的值是：" + task.atomicCount.get());
        System.out.println("volatileCount 的值是：" + task.volatileCount);

    }

    static class Task {
        AtomicInteger atomicCount = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic() {
            atomicCount.getAndIncrement();
        }

        void incrementVolatile() {
            volatileCount++;
        }
    }
}
