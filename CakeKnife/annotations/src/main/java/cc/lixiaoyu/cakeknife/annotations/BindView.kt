package cc.lixiaoyu.cakeknife.annotations

/**
 * Author: lixiaoyu.owen
 * Time: 2021/3/31
 * Desc: @BindView(R.id.button) 用来 View 注入
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class BindView(val value: Int = -1)