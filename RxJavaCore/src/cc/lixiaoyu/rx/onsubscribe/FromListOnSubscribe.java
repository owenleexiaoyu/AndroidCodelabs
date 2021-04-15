package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.observers.Subscriber;

import java.util.List;

public class FromListOnSubscribe<T> implements Observable.OnSubscribe<T> {

    final List<T> list;

    public FromListOnSubscribe(List<T> list) {
        this.list = list;
    }

    @Override
    public void call(Subscriber<T> subscriber) {
        if (list != null) {
            for (T item : list) {
                subscriber.onNext(item);
            }
        }
    }
}
