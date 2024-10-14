package life.lixiaoyu.multithread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示 ReentrantLock 读写锁的使用
 */
public class AATReentrantLockReadWriteDemo {

    static class ReadWriteTask {
        private final ReentrantReadWriteLock.ReadLock readLock;
        private final ReentrantReadWriteLock.WriteLock writeLock;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public ReadWriteTask() {
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }

        /**
         * 模拟在线文档的查看
         */
        void read() {
            String name = Thread.currentThread().getName();
            try {
                readLock.lock();
                System.out.println(name + " 正在读取数据");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                System.out.println(name + " 释放了读锁");
            }
        }

        /**
         * 模拟在线文档的编辑
         */
        void write() {
            String name = Thread.currentThread().getName();
            try {
                writeLock.lock();
                System.out.println(name + " 正在写入数据");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
                System.out.println(name + " 释放了写锁");
            }
        }
    }

    public static void main(String[] args) {
        final ReadWriteTask task = new ReadWriteTask();

        // 模拟三个线程同时读，三个线程同时写
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.read();
                }
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.write();
                }
            }).start();
        }
    }
}
