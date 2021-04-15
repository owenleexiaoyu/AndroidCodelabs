package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.observers.Subscriber;

public class FromArrayOnSubscribe<T> implements Observable.OnSubscribe<T> {
    final T[] array;

    public FromArrayOnSubscribe(T[] array) {
        this.array = array;
    }

    @Override
    public void call(Subscriber<T> subscriber) {
        if (array != null) {
            for (T item : array) {
                subscriber.onNext(item);
            }
        }
    }
}
