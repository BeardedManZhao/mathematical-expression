# 1.5.6 -> 1.5.7 版本更新日志

### 更新时间：2025年05月13日

==Java==

- 更新版本号为 1.5.7
- 为 JvmExpressionFunction 添加了 explain 支持！

```java
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.JvmExpressionFunction;

public class MAIN {

    public static void main(String[] args) {
        JvmExpressionFunction parse = JvmExpressionFunction.parse("f(x, y) = y * x + 1 - x");
        long l = System.currentTimeMillis();
        System.out.println(parse.explain(1024, "xy"));
        System.out.println(parse.explain(1000, "xy"));
        System.out.println(System.currentTimeMillis() - l);
    }
}
```

- 为 JvmExpression 添加了 getExpressionStrAndNumber 支持！能够获取到完整的表达式！

```java
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;

public class MAIN {

    public static void main(String[] args) {
        JvmExpression compile = JvmExpression.compile("1 + 2");
        System.out.println(compile.calculation(false));
        System.out.println(compile.getExpressionStrAndNumber());
        compile.setParamNumber(0, 2);
        System.out.println(compile.getExpressionStrAndNumber());
    }
}
```

- 为 EquationSolverExpressionTwo 添加了 explain 支持！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolvingTwo;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolverExpression;

public class MAIN {

    public static void main(String[] args) {
        final SingletonEquationSolvingTwo instance = (SingletonEquationSolvingTwo) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving2);
        EquationSolverExpression compile = instance.compile("1 + x + 4 = 10", false);
        String explain = compile.explain();
        System.out.println(explain);

        compile.setKnownNumber(0, 5);
        System.out.println(compile.explain());
    }
}
```