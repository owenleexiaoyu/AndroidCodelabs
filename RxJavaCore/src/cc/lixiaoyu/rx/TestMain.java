package cc.lixiaoyu.rx;

import cc.lixiaoyu.rx.func.Predicate;
import cc.lixiaoyu.rx.func.Transformer;
import cc.lixiaoyu.rx.observers.NextAction;
import cc.lixiaoyu.rx.observers.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
//        testOperators();
//        testObserveOn();
//        testFromList();
//        testFromArray();
        testJust();
    }

    /**
     * Test filter and map operator
     */
    private static void testOperators() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 4;
            }
        }).map(new Transformer<Integer, String>() {
            @Override
            public String call(Integer from) {
                return "mapping" + from;
            }
        }).subscribe(new NextAction<String>() {
            @Override
            public void onNext(String var1) {
                System.out.println(var1);
            }
        });

    }

    /**
     * Test subscribeOn and observeOn
     */
    private static void testObserveOn() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<Integer> subscriber) {
                System.out.println("OnSubscribe@" + Thread.currentThread().getName());
                subscriber.onNext(1);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new NextAction<Integer>() {
                    @Override
                    public void onNext(Integer var1) {
                        System.out.println("Subscriber@" + Thread.currentThread().getName());
                        System.out.println(var1);
                    }
                });
    }

    /**
     * 测试从list中创建数据源
     */
    private static void testFromList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        Observable.from(list).subscribe(new NextAction<String>() {
            @Override
            public void onNext(String var1) {
                System.out.println(var1);
            }
        });
    }

    /**
     * 测试从数组中创建数据源
     */
    private static void testFromArray() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        Observable.from(array).subscribe(new NextAction<Integer>() {
            @Override
            public void onNext(Integer var1) {
                System.out.println(var1);
            }
        });
    }

    /**
     * 测试只发送一个数据
     */
    private static void testJust() {
        Observable.just("hello world").subscribe(new NextAction() {
            @Override
            public void onNext(Object var1) {
                System.out.println(var1);
            }
        });
    }
}
