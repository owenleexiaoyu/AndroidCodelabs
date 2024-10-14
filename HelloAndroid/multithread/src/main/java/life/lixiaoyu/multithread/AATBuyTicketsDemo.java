package life.lixiaoyu.multithread;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过买票场景来讲述多线程安全
 */

public class AATBuyTicketsDemo {

    static List<String> tickets = new ArrayList<String>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            tickets.add("票_" + (i + 1));
        }
        buyTickets();
    }

    private static void buyTickets() {
//        final BuyTicketsSynchronized buyer = new BuyTicketsNonSynchronized();
        final BuyTicketsSynchronized buyer = new BuyTicketsSynchronized();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    buyer.printBuyTicketInfo();
                }
            }).start();
        }
    }

    /**
     * 不是多线程安全的操作
     */
    static class BuyTicketsNonSynchronized {
        void printBuyTicketInfo() {
            String name = Thread.currentThread().getName();
            System.out.println("买票人是：" + name + " 准备好了....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("买票人：" + name + " 买到的票是：" + tickets.remove(0));
        }
    }

    /**
     * 是多线程安全的操作
     */
    static class BuyTicketsSynchronized {
        void printBuyTicketInfo() {
            String name = Thread.currentThread().getName();
            System.out.println("买票人是：" + name + " 准备好了....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 买票的操作加上 synchronized 关键字进行加锁
            synchronized (this) {
                System.out.println("买票人：" + name + " 买到的票是：" + tickets.remove(0));
            }
        }
    }
}
