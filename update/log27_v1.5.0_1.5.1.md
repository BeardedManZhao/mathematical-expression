# 1.5.0 -> 1.5.1 版本更新日志

### 更新时间：2025年05月07日

==Java==

- 更新版本号为 1.5.1
- 添加了 `JvmExpressionFunction` 类，用于直接操作 JVM 进行函数表达式计算，性能优越。

```java
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.JvmExpressionFunction;

public class MAIN {

    public static void main(String[] args) {
        JvmExpressionFunction.registerFunction("f1(x) = x + 1");
        JvmExpressionFunction compile = JvmExpressionFunction.parse("f(x, y) = f1(x) + y");
        long l = System.currentTimeMillis();
        System.out.println(compile.run(1, 2));
        System.out.println(System.currentTimeMillis() - l);
    }
}
```

- 实现了一元方程求解

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolving;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolver;

public class MAIN {

    public static void main(String[] args) {
        SingletonEquationSolving instance = (SingletonEquationSolving) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving);
        EquationSolver compile = instance.compile("2 * x - x = 10", false);
        // 设置允许的最大迭代次数
        compile.setMaxIter(100);
        try {
            // 使用牛顿求解计算
            System.out.println(compile.calculation(false));
        } catch (ArithmeticException e) {
            System.out.println("正在切换求解方案，因为牛顿法失败了：" + e.getMessage());
            // 关闭牛顿求解 然后计算 这样使用的就是二分法求解
            compile.setUseNewton(false);
            System.out.println(compile.calculation(false));
        }
    }
}
```