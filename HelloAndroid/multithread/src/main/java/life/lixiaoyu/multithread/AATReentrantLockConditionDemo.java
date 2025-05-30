package life.lixiaoyu.multithread;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者的场景
 * 利用 ReentrantLock Condition 条件对象，能够指定唤醒某个线程来工作
 *
 * 生产者是 Boss，生产砖。砖的序号是偶数让工人 2 搬，奇数让工人 1 搬
 * 消费者是 工人 1 和 工人 2，有砖搬就搬，没有砖搬就休息
 */
public class AATReentrantLockConditionDemo {

    static class ConditionTask {
        private Condition worker1Condition, worker2Condition;
        ReentrantLock lock = new ReentrantLock(true);
        volatile int flag = 0; // 砖的序列号

        public ConditionTask() {
            worker1Condition = lock.newCondition();
            worker2Condition = lock.newCondition();
        }

        void work1() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 == 0) {
                    System.out.println("Worker1 无砖可搬，休息会");
                    worker1Condition.await();
                }
                System.out.println("Worker1 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void work2() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 != 0) {
                    System.out.println("Worker2 无砖可搬，休息会");
                    worker2Condition.await();
                }
                System.out.println("Worker2 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void boss() {
            try {
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag % 2 == 0) {
                    worker2Condition.signal();
                    System.out.println("生产出来了砖，唤醒工人 2 去搬：" + flag);
                } else {
                    worker1Condition.signal();
                    System.out.println("生产出来了砖，唤醒工人 1 去搬：" + flag);
                }
            } finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        final ConditionTask task = new ConditionTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    task.work1();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    task.work2();
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            task.boss();
        }
    }
}
