package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.Scheduler;
import cc.lixiaoyu.rx.observers.Subscriber;

public class SubscribeOnOnSubscribe<T> implements Observable.OnSubscribe<T> {
    final Observable<T> source;
    final Scheduler scheduler;

    public SubscribeOnOnSubscribe(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void call(Subscriber subscriber) {
        subscriber.onStart();
        scheduler.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                source.onSubscribe.call(subscriber);
            }
        });
    }
}
