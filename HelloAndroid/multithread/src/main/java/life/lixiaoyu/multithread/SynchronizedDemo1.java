package life.lixiaoyu.multithread;

public class SynchronizedDemo1 implements TestDemo {

    // 没有线程同步
    // private boolean running = true;

    // 使用 volatile 关键字做线程同步
    private volatile boolean running = true;

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                while(running) {
                    // 这里不要打印，IO 阻塞会刷新工作内存
                    // System.out.println("Thread is running!");
                }
            }
        }.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }

    private void stop() {
        running = false;
    }
}