package cc.lixiaoyu.rx.onsubscribe;

import cc.lixiaoyu.rx.Observable;
import cc.lixiaoyu.rx.Scheduler;
import cc.lixiaoyu.rx.observers.Subscriber;

public class ObserveOnOnSubscribe<T> implements Observable.OnSubscribe<T> {
    final Observable<T> source;
    final Scheduler scheduler;

    public ObserveOnOnSubscribe(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void call(Subscriber<T> subscriber) {

        Scheduler.Worker worker = scheduler.createWorker();
        source.onSubscribe.call(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                worker.schedule(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onCompleted();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                worker.schedule(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onError(t);
                    }
                });
            }

            @Override
            public void onNext(T var1) {
                worker.schedule(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onNext(var1);
                    }
                });
            }
        });
    }
}
