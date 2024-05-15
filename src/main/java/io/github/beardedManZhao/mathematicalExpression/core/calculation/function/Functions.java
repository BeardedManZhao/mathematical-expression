package io.github.beardedManZhao.mathematicalExpression.core.calculation.function;

import java.lang.annotation.*;

/**
 * 能够作用在类中的一个函数注册注解，您可以将这个注解用于一个类中，然后将这个类的对象直接注册到数学表达式解析库中，就可以将当前直接中所有的公式进行注册的效果。
 * <p>
 * A function registration annotation that can be applied to a class, you can use this annotation to apply to a class, and then directly register the object of this class in the mathematical expression parsing library, which can register all the formulas in the current direct method.
 *
 * @author zhao
 */
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Functions {

    /**
     * @return 所有需要被注册的数学函数表达式公式，例如 f(x) = x * x
     * <p>
     * All mathematical function expression formulas that need to be registered, such as f (x)=x * x
     */
    String[] value();
}
