package life.lixiaoyu.multithread;

public class SynchronizedDemo2 implements TestDemo {

    private int x = 0;

    // 使用 synchronized 保证对资源读写的同步性和原子性
    private synchronized void count() {
        x++;
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
