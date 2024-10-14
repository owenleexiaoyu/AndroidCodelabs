package life.lixiaoyu.multithread;

public class ThreadStopDemo implements TestDemo {
    @Override
    public void runTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    System.out.println("number: " + i);
                }
            }
        };
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.stop();
    }
}
