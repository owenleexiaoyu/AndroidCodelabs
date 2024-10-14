package life.lixiaoyu.multithread;

public class ThreadLocalDemo implements TestDemo {
    @Override
    public void runTest() {
        final ThreadLocal<Integer> threadNumber = new ThreadLocal<>();
        new Thread() {
            @Override
            public void run() {
                threadNumber.set(1);
                System.out.println(Thread.currentThread().getName() + " get: " + threadNumber.get());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                threadNumber.set(2);
                System.out.println(Thread.currentThread().getName() + " get: " + threadNumber.get());
            }
        }.start();
    }
}
