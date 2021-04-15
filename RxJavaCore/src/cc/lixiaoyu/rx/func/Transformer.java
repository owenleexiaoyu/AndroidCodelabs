package cc.lixiaoyu.rx.func;

public interface Transformer<T, R> {
    R call(T from);
}
