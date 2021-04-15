package cc.lixiaoyu.eventcar

object EventCar {

    // 所有标记了@Subscribe注解的方法Map，按照订阅者的类型来分组
    private val cacheMap: MutableMap<Any, List<SubscribeMethod>> = HashMap()

    /**
     * 注册订阅者
     * @param subscriber 订阅者：Activity、Fragment
     */
    fun register(subscriber: Any) {
        var subscribeMethods = cacheMap[subscriber]
        if (subscribeMethods == null) {
            subscribeMethods = getSubscribeList(subscriber)
            cacheMap[subscriber] = subscribeMethods
        }
    }

    private fun getSubscribeList(subscriber: Any): List<SubscribeMethod> {
        val list : MutableList<SubscribeMethod> = arrayListOf()
        var aClass = subscriber.javaClass
        while (aClass != null) {
            aClass = aClass.superclass as Class<Any>
        }
    }

    /**
     * 发送事件
     * @param obj
     */
    fun post(obj: Any) {

    }

    /**
     * 注销订阅者：停止接收事件
     * @param subscriber
     */
    fun unRegister(subscriber: Any) {

    }
}