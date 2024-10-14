package life.lixiaoyu.multithread;

public class HandlerThreadDemo implements TestDemo {
    @Override
    public void runTest() {
        HandlerThread thread = new HandlerThread();
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        thread.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahahahaha");
            }
        });
        thread.quit();
    }
}
