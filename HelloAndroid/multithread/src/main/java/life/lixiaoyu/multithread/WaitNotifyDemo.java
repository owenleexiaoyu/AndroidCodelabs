package life.lixiaoyu.multithread;

public class WaitNotifyDemo implements TestDemo {

    private String name;

    private synchronized void initName() {
        name = "Sam";
        System.out.println("Init name finish, notifyAll");
        notifyAll();
    }

    private synchronized void printName() {
        while (name == null) {
            try {
                System.out.println("Name is null, wait");
                wait();
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Name is " + name);
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
                initName();
            }
        };
        thread1.start();
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                printName();
            }
        };
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread end");
    }
}
