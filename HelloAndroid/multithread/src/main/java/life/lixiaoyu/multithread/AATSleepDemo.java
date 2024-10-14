package life.lixiaoyu.multithread;

public class AATSleepDemo implements TestDemo {

    private final Object object = new Object();

    @Override
    public void runTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {

                synchronized (object) {
                    try {
                        System.out.println("Run: thread1 start");
                        Thread.sleep(2000);
                        System.out.println("Run: thread1 end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("Run: thread2 start");
                    System.out.println("Run: thread2 end");
                }

            }
        };
        thread1.start();
        thread2.start();
    }
}
