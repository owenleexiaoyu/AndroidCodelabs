package life.lixiaoyu.multithread;

public class SingleManager {

    /**
     * 这里使用 volatile 关键字来禁用指令重排
     * 因为 sInstance = new SingleManager(); 这个操作包含几个 zi
     */
    private static SingleManager sInstance;

    private SingleManager() {}

//    public static SingleManager getInstance() {
//        if (sInstance == null) {
//            synchronized (SingleManager.class) {
//                if (sInstance == null) {
//                    sInstance = new SingleManager();
//                }
//            }
//        }
//        return sInstance;
//    }

    public static SingleManager getInstance() {
        if (sInstance == null) {
            sInstance = new SingleManager();
        }
        return sInstance;
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SingleManager ins = SingleManager.getInstance();
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + ins.toString());
            }
        };
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }
}
