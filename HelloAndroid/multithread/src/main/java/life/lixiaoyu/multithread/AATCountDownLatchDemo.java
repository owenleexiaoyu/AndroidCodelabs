package life.lixiaoyu.multithread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 演示一个多人过山车的场景
 * 假设有 5 人去坐过山车，需要等待 5 人都准备好，才能发车
 */
public class AATCountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(1000, 4000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 准备好了");
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有人都准备好了，准备发车...");
    }
}
