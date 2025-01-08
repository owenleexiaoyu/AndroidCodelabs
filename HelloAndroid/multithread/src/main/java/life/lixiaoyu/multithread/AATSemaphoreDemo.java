package life.lixiaoyu.multithread;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 演示 多人景点游玩，但是同一时刻限流 3 人
 */
public class AATSemaphoreDemo {

    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(3, true);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        String name = Thread.currentThread().getName();
                        System.out.println(name + " 获取到了许可证，进去游玩了");
                        Thread.sleep(new Random().nextInt(2000, 5000));
                        semaphore.release();
                        System.out.println(name + " 归还了许可证");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
