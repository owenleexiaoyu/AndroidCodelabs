package life.lixiaoyu.helloandroid.async;

import android.os.Looper;

public class LooperThread extends Thread {

    LooperThread(String name) {
        super(name);
    }

    private Looper looper;

    Looper getLooper() {
        synchronized (this) {
            if(looper == null && isAlive()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return looper;
    }

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            looper = Looper.myLooper();
            notify();
        }
        Looper.loop();
    }

}
