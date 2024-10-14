package life.lixiaoyu.multithread;

public class SynchronizedDemo3 implements TestDemo {
    private int x = 0;
    private int y = 0;
    private String name = "";

    private final Object lock = new Object();

    private synchronized void count(int newValue) {
        System.out.println("count start");
        x = newValue;
        y = newValue;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        System.out.println("count end");
    }

    private synchronized void minus(int delta) {
        System.out.println("minus start");
        x -= delta;
        y -= delta;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        System.out.println("minus end");
    }

    private void setName(String newName) {
        synchronized (lock) {
            System.out.println("setName start");
            name = newName;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.println("setName end");
        }
    }

    public static void testStatic() {
        synchronized (SynchronizedDemo3.class) {
            System.out.println("testStatic invoke");
        }
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                count(2);
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                setName("Sam");
            }
        });
        thread2.start();
    }
}
