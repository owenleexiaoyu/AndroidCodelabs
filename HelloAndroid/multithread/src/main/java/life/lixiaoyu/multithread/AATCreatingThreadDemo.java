package life.lixiaoyu.multithread;

/**
 * AAT 教程中线程的几种创建方式
 */
public class AATCreatingThreadDemo implements TestDemo {

    @Override
    public void runTest() {

        // 复写 Thread.run 方法
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started");
            }
        }.start();

        // 传入 Runnable 对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started");
            }
        }).start();
    }
}
