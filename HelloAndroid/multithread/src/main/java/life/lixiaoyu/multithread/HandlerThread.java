package life.lixiaoyu.multithread;

import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerThread extends Thread {

    Looper looper = new Looper();
    private Runnable task;

    private AtomicBoolean quit = new AtomicBoolean(false);

    void quit() {
        quit.set(true);
    }

    synchronized void setTask(Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        looper.loop();
    }

    class Looper {
        void loop() {
            while (!quit.get()) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
