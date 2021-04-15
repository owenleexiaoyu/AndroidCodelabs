package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.func.Predicate;
import cc.lixiaoyu.rx.observers.Subscriber;

/**
 * 桥接Observable 中的 OnSubscribe
 * @param <T>
 */
public class FilterOnSubscribe<T> implements Observable.OnSubscribe<T> {

    final Observable<T> source;
    final Predicate<T> predicate;

    public FilterOnSubscribe(Observable<T> source, Predicate<T> predicate) {
        this.source = source;
        this.predicate = predicate;
    }

    @Override
    public void call(Subscriber<T> subscriber) {
        source.subscribe(new FilterSubscriber<T>(subscriber, predicate));
    }

    /**
     * 桥接 Observable 中的 Subscriber
     * @param <T>
     */
    public static class FilterSubscriber<T> extends Subscriber<T> {
        final Subscriber<T> actual;
        final Predicate<T> predicate;

        public FilterSubscriber(Subscriber<T> actual, Predicate<T> predicate) {
            this.actual = actual;
            this.predicate = predicate;
        }

        @Override
        public void onCompleted() {
            actual.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            actual.onError(t);
        }

        @Override
        public void onNext(T var1) {
            if (predicate.test(var1)) {
                actual.onNext(var1);
            }
        }
    }
}
