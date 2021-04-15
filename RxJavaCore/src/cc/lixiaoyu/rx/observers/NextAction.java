package cc.lixiaoyu.rx.observers;

public abstract class NextAction<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable t) {

    }
}
