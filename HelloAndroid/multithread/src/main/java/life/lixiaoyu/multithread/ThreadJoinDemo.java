package life.lixiaoyu.multithread;

public class ThreadJoinDemo implements TestDemo {
    private String name;

    private void initName() {
        name = "Sam";
    }

    private synchronized void printName() {
        System.out.println("Name is " + name);
    }

    @Override
    public void runTest() {
        final Thread thread1 = new Thread() {
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
                try {
                    thread1.join();
                } catch (InterruptedException e) {

                }
                printName();
            }
        };
        thread2.start();
    }
}
