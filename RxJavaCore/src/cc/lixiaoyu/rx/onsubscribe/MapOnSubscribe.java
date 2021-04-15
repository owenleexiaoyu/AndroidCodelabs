package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.observers.Subscriber;
import cc.lixiaoyu.rx.func.Transformer;

/**
 * 桥接Observable 中的 OnSubscribe
 * @param <T>
 * @param <R>
 */
public class MapOnSubscribe<T, R> implements Observable.OnSubscribe<R> {
    final Observable<T> source;
    final Transformer<T, R> transformer;

    public MapOnSubscribe(Observable<T> source, Transformer<T, R> transformer) {
        this.source = source;
        this.transformer = transformer;
    }

    @Override
    public void call(Subscriber<R> subscriber) {
        source.subscribe(new MapSubscriber<R, T>(subscriber, transformer));
    }

    /**
     * 桥接Observable 中的 Subscriber
     * @param <T>
     * @param <R>
     */
    public static class MapSubscriber<T, R> extends Subscriber<R> {
        final Subscriber<T> actual;
        final Transformer<R, T> transformer;

        public MapSubscriber(Subscriber<T> actual, Transformer<R, T> transformer) {
            this.actual = actual;
            this.transformer = transformer;
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
        public void onNext(R var1) {
            actual.onNext(transformer.call(var1));
        }
    }

}
