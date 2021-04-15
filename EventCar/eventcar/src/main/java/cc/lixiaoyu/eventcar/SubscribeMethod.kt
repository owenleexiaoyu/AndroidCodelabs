package cc.lixiaoyu.eventcar

import java.lang.reflect.Method

data class SubscribeMethod(
    // 使用了注解的方法
    var method: Method,
    // 执行方法的线程
    var threadMode: ThreadMode,
    // 接收的event类型
    var eventType: Class<*>
)
