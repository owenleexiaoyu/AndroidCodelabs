package life.lixiaoyu.multithread;

public class AATThreadPoolTest {
    public static void main(String[] args) {
        int capacity = (1 << 29) - 1;
        int running = -1 << 29;
        int shutdown = 0 << 29;
        int stop = 1 << 29;
        int tidying = 2 << 29;
        int terminated = 3 << 29;

        String bsCapacity = Integer.toBinaryString(capacity);
        String bsRunning = Integer.toBinaryString(running);
        String bsShutdown = Integer.toBinaryString(shutdown);
        String bsStop = Integer.toBinaryString(stop);
        String bsTidying = Integer.toBinaryString(tidying);
        String bsTerminated = Integer.toBinaryString(terminated);

        // 当线程池中有一个线程时 ctl 的情况
        int ctl = running | 1;
        String bsCtl = Integer.toBinaryString(ctl);

        System.out.println("running         :" + bsRunning);
        System.out.println("shutdown        :" + bsShutdown.repeat(32));
        System.out.println("stop            :00" + bsStop);
        System.out.println("tidying         :0" + bsTidying);
        System.out.println("terminated      :0" + bsTerminated);

        System.out.println("capacity        :000" + bsCapacity);
        System.out.println("ctl             :" + bsCtl);

    }
}
