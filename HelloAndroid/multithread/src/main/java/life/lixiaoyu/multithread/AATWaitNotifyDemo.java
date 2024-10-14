package life.lixiaoyu.multithread;

public class AATWaitNotifyDemo implements TestDemo {

    private final Object object = new Object();
    private volatile boolean hasNotify = false;

    @Override
    public void runTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Run: thread1 start");
                synchronized (object) {
                    try {
                        if (!hasNotify) {
                            // 没有 notify 时，才 wait，并且加上超时时间
                            object.wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Run: thread1 end");
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                System.out.println("Run: thread2 start");
                synchronized (object) {
                    object.notify();
                    hasNotify = true;
                }
                System.out.println("Run: thread2 end");
            }
        };
        thread2.start();
        thread1.start();
    }
}
