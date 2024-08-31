package life.lixiaoyu.multithread;

public class SynchronizedDemo3 implements TestDemo {
    private int x = 0;
    private int y = 0;
    private String name = "";

    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
    }

    private synchronized void setName(String newName) {
        name = newName;
    }

    @Override
    public void runTest() {

    }
}
