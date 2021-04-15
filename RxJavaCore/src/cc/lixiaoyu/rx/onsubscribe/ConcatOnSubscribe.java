package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.observers.Subscriber;
// TODO add concat
public class ConcatOnSubscribe<T> implements Observable.OnSubscribe<T> {
    final Observable<T> source1;
    final Observable<T> source2;

    public ConcatOnSubscribe(Observable<T> source1, Observable<T> source2) {
        this.source1 = source1;
        this.source2 = source2;
    }

    @Override
    public void call(Subscriber<T> subscriber) {

    }
}
