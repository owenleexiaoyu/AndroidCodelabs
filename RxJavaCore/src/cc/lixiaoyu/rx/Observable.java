package cc.lixiaoyu.rx;

import cc.lixiaoyu.rx.func.Predicate;
import cc.lixiaoyu.rx.func.Transformer;
import cc.lixiaoyu.rx.observers.Subscriber;
import cc.lixiaoyu.rx.onsubscribe.*;

import java.util.List;

/**
 * 订阅源
 */
public class Observable<T> {

    public final OnSubscribe<T> onSubscribe;

    private Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    /**
     * 创建一个数据源的被观察者
     * @param onSubscribe
     * @param <T>
     * @return
     */
    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<T>(onSubscribe);
    }

    /**
     * 订阅者订阅数据源
     * @param subscriber
     */
    public void subscribe(Subscriber<T> subscriber) {
        subscriber.onStart();
        onSubscribe.call(subscriber);
    }

    /**
     * 只发送一个数据源
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Observable<T> just(final T value) {
        return create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<T> subscriber) {
                subscriber.onNext(value);
            }
        });
    }

    /**
     * 从一个列表中创建数据源
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Observable<T> from(List<T> list) {
        return create(new FromListOnSubscribe<>(list));
    }

    /**
     * 从一个数组中创建数据源
     * @param array
     * @param <T>
     * @return
     */
    public static <T> Observable<T> from(T[] array) {
        return create(new FromArrayOnSubscribe<>(array));
    }

    /**
     * 转换数据源类型
     * @param transformer
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Transformer<T, R> transformer) {
        return create(new MapOnSubscribe<T, R>(this, transformer));
    }

    /**
     * 过滤器，符合条件的才会发送给订阅者
     * @param predicate
     * @return
     */
    public Observable<T> filter(Predicate<T> predicate) {
        return create(new FilterOnSubscribe<T>(this, predicate));
    }

    /**
     * 指定发送数据源的线程
     * @param scheduler
     * @return
     */
    public Observable<T> subscribeOn(Scheduler scheduler) {
        return Observable.create(new SubscribeOnOnSubscribe<T>(this, scheduler));
    }

    /**
     * 指定订阅者观察的线程
     * @param scheduler
     * @return
     */
    public Observable<T> observeOn(Scheduler scheduler) {
        return Observable.create(new ObserveOnOnSubscribe<T>(this, scheduler));
    }

    public interface OnSubscribe<T> {
        void call(Subscriber<T> subscriber);
    }

}