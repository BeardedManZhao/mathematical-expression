# 1.5.1 -> 1.5.3 版本更新日志

### 更新时间：2025年05月07日

==Java==

- 更新版本号为 1.5.3
- Jvm 计算组件的检查 check 方法 进行了修改，其将不会参与到 jvm 编译器的字节码检查中，为了更好的特性！
- 修复了 Jvm 计算组件的 空指针错误
- 实现了 JvmExpressionFunction 和 ExpressionFunction 的互相转换！

```java
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ExpressionFunction;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.JvmExpressionFunction;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        ExpressionFunction expressionFunction0 = ExpressionFunction.parse("test(a,b,c)=a+b+c");
        // 可以将一个 表达式函数 转换成一个 JVM 函数
        // 而 jvm 函数也可以直接被当作 表达式函数 使用
        JvmExpressionFunction expressionFunction1 = JvmExpressionFunction.parse(expressionFunction0);
        // 处理 explain 其它的都可以使用
        System.out.println(expressionFunction0.getParamSize());
        System.out.println(expressionFunction1.getParamSize());
        System.out.println(expressionFunction0.run(1, 2, 3));
        System.out.println(expressionFunction1.run(1, 2, 3));
        System.out.println(expressionFunction0);
        System.out.println(expressionFunction1);
        System.out.println(expressionFunction0.explain(1, 2, 3));
        // TODO JVM 函数的 explain 暂时不支持 因为其完全使用的 class 字节码运行
        // System.out.println(expressionFunction1.explain(1, 2, 3));
    }
}
```