package life.lixiaoyu.multithread;

import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedDemo2_1 implements TestDemo {

    // 使用原子类代替 synchronized 关键字保证 x 变量自增的同步性和原子性
    private AtomicInteger x = new AtomicInteger(0);

    private void count() {
        x.incrementAndGet();
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from thread 1:" + x);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from thread 2:" + x);
            }
        }.start();
    }

}
