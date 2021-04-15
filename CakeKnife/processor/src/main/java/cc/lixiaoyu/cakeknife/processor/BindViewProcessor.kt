package cc.lixiaoyu.cakeknife.processor

import cc.lixiaoyu.cakeknife.annotations.BindView
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.util.ArrayList
import java.util.HashMap
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

class BindViewProcessor : AbstractProcessor() {

    private lateinit var elementUtils: Elements // 对 Element 的工具类
    private lateinit var typeUtils: Types  // 对 TypeMirror 的工具类
    private lateinit var filer: Filer  // 用来创建文件
    private lateinit var messager: Messager  // 用来打印错误信息

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) {
            return
        }
        elementUtils = processingEnv.elementUtils
        typeUtils = processingEnv.typeUtils
        filer = processingEnv.filer
        messager = processingEnv.messager
    }

    override fun process(set: MutableSet<out TypeElement>?,
                         roundEnv: RoundEnvironment?): Boolean {
        if (roundEnv == null) {
            error(null, "roundEnv is null")
        }
        processBindView(roundEnv!!)
        return true
    }



    private fun processBindView(roundEnv: RoundEnvironment) {
        // 获取所有使用了BindView注解的元素
        val bindViewElements = roundEnv.getElementsAnnotatedWith(BindView::class.java)
        // 根据不同的类来对使用了注解的元素进行分组，每一个类创建一个ViewBinding类
        val groupedElement = groupingElementWithType(bindViewElements)
        generateViewBindingClasses(groupedElement)
    }

    private fun generateViewBindingClasses(groupedElement: Map<TypeElement, ArrayList<Element>>) {
        // 遍历每一个类
        val keySet = groupedElement.keys
        for (classItem in keySet) {
            // 创建一个XXActivity_ViewBinding辅助类
            val typeBuilder = makeTypeSpecBuilder(classItem)
            // 创建构造函数
            val constructorBuilder = makeConstructor(classItem)
            // 生成构造函数中的代码
            buildConstructorCode(constructorBuilder, groupedElement[classItem])
            // 把构造函数加到创建的辅助类中
            typeBuilder.addMethod(constructorBuilder.build())
            // 生成一个Java文件
            val file = JavaFile.builder(getPackageName(classItem), typeBuilder.build()).build()
            // 写入该Java文件
            file.writeTo(this.processingEnv.filer)
        }
    }

    /**
     * 创建一个XXActivity_ViewBinding辅助类
     */
    private fun makeTypeSpecBuilder(classItem: TypeElement): TypeSpec.Builder {
        return TypeSpec.classBuilder("${classItem.simpleName}_ViewBinding")
                .addModifiers(Modifier.PUBLIC)
    }

    /**
     * 创建一个构造函数，传入一个类型是目标Activity的形参target
     */
    private fun makeConstructor(classItem: TypeElement): MethodSpec.Builder {
        val typeMirror = classItem.asType()
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(typeMirror), "target")
    }

    /**
     * 生成构造函数中的代码
     * 对这个类中每个加了注解的控件属性进行findViewById的初始化
     * 形如 target.mTextView = target.findViewById(2131165289);
     */
    private fun buildConstructorCode(bindMethodBuilder: MethodSpec.Builder, elements: ArrayList<Element>?) {
        elements?.let {
            for (itemView in elements) {
                bindMethodBuilder.addStatement("target.$itemView = " +
                        "target.findViewById(${itemView.getAnnotation(BindView::class.java).value})")
            }
        }
    }

    /**
     * 获取类的包名
     */
    private fun getPackageName(typeElement: Element): String {
        var ele = typeElement
        while(ele.kind != ElementKind.PACKAGE){
            ele = ele.enclosingElement
        }
        return (ele as PackageElement).qualifiedName.toString()
    }

    /**
     * 对所有加了注解的元素按类名进行分组
     */
    private fun groupingElementWithType(eleSet: Set<Element>): Map<TypeElement, ArrayList<Element>> {
        // 定义了一个 <Class, List<Element>> 的map，属于同一个类的被 @BindView 修饰的元素都会被添加到list中
        val groupedElement = HashMap<TypeElement, ArrayList<Element>>()
        // 遍历所有被 @BindView 修饰的元素
        for (item in eleSet) {
            // 检查使用注解元素的合法性（只能是变量，不能加private和final修饰符）
            try {
                checkAnnotationLegal(item)
                // 获取到这个元素所在类元素信息
                val enclosingElement = item.enclosingElement as TypeElement
                // 如果这个类已经在map中有了，就直接添加到对应的list中
                if (groupedElement.containsKey(enclosingElement)) {
                    groupedElement[enclosingElement]?.add(item)
                } else {
                    // 如果这个类在map中没有，就put到map中，并新建一个list用来存放这个元素
                    val list = ArrayList<Element>()
                    list.add(item)
                    groupedElement[enclosingElement] = list
                }
            } catch (e: RuntimeException) {
                // 捕获到异常，打印错误信息
                error(item, e.message ?: "Something goes wrong in checkAnnotationLegal()")
            }
        }
        // 返回这个分组结果
        return groupedElement
    }

    /**
     * 检查@BindView注解修饰的变量进行合法检查
     * @param element
     */
    private fun checkAnnotationLegal(element: Element) {
        // 首先看注解修饰的是不是变量
        if (element.kind != ElementKind.FIELD) {
            throw RuntimeException("@BindView must in filed! Current kind is " + element.kind)
        }
        // 获取元素的修饰符
        val modifiers = element.modifiers
        // 看修饰符中有没有 final
        if (modifiers.contains(Modifier.FINAL)) {
            throw RuntimeException("@BindView filed can not be final")
        }
        // 看修饰符中有没有 private
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw RuntimeException("@BindView filed can not be private")
        }
    }



    private fun error(element: Element?, msg: String, vararg args: Any?) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), element)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val annotations = mutableSetOf<String>()
        annotations.add(BindView::class.java.canonicalName)
        return annotations
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}