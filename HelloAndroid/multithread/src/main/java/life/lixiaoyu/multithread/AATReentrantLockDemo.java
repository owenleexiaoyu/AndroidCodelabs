package life.lixiaoyu.multithread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 ReentrantLock 公平锁和非公平锁的不同效果
 */
public class AATReentrantLockDemo {

    static class ReentrantLockTask {
        // 非公平锁
        ReentrantLock lock = new ReentrantLock();

        // 公平锁
//        ReentrantLock lock = new ReentrantLock(true);
        void print() {
            String name = Thread.currentThread().getName();
            // 打印两次
            try {
                lock.lock();
                System.out.println(name + " 第一次打印");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            try {

                lock.lock();
                System.out.println(name + " 第二次打印");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.print();
            }
        };
        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
        }
    }
}
