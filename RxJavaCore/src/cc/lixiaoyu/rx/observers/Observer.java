package cc.lixiaoyu.rx.observers;

/**
 * 观察者的接口
 * @param <T>
 */
public interface Observer<T> {
    void onCompleted();
    void onError(Throwable t);
    void onNext(T var1);
}
