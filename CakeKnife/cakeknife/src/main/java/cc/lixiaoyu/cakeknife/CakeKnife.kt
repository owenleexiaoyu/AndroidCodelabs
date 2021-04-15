package cc.lixiaoyu.cakeknife

import android.app.Activity
import java.lang.RuntimeException
import java.lang.reflect.Constructor

object CakeKnife {

    @JvmStatic
    fun bind(activity: Activity) {
        // 拿到当前绑定的类
        val targetClass = activity::class.java
        // 通过类名找到APT生成的XXActivity_ViewBinding类的构造函数
        val constructor = findBindingConstructorForClass(targetClass)
        // 通过反射来创建一个实例，在构造函数中调用findViewById来绑定控件。
        constructor?.newInstance(activity)
    }

    private fun findBindingConstructorForClass(cls: Class<*>?) : Constructor<*>?{
        cls?.let {
            // 获取到类名
            val clsName = cls.name
            return try {
                // 通过ClassLoader来加载这个APT生成的类
                val bindingClass = cls.classLoader?.loadClass(clsName + "_ViewBinding")
                // 获取到这个类的构造函数
                bindingClass?.getConstructor(cls)
            } catch (e: ClassNotFoundException) {
                // 如果没有找到这个类，则传入父类继续查找
                findBindingConstructorForClass(cls.superclass)
            } catch (e: NoSuchMethodException) {
                // 如果这个类没有构造方法，则抛出异常
                throw RuntimeException("Unable to find binding constructor for $clsName")
            }
        }
        return null
    }

}