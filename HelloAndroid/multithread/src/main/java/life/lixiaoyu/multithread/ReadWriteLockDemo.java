package life.lixiaoyu.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo implements TestDemo {

    private int x = 0;

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    private void count() {
        try {
            writeLock.lock();
            x++;
            // 可能抛异常
        } finally {
            writeLock.unlock();
        }
    }

    private void print() {
        try {
            readLock.lock();
            System.out.println("x = " + x);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void runTest() {

    }
}
