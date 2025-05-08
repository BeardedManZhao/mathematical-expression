# 1.5.1 -> 1.5.3 版本更新日志

### 更新时间：2025年05月07日

==Java==

- 更新版本号为 1.5.4
- 优化方程求解器，修复了其调用JVM函数时容易出现错误的问题

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolving;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolver;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions("sum(x, y, z) = x + y + z")
public class MAIN {

    public static void main(String[] args) {
        Mathematical_Expression.register_jvm_function(MAIN.class);
        SingletonEquationSolving instance = SingletonEquationSolving.getInstance("123");
        EquationSolver compile = instance.compile("x + sum(1,2,3) = 0", false);
        compile.setUseNewton(false);
        compile.setBisectionLeft(-100);
        compile.setBisectionRight(100);
        CalculationNumberResults calculation = compile.calculation(false);
        System.out.println(calculation.getResult());
    }
}
```

- 新增 `SingletonEquationSolvingTwo` 求解器，其相较于 `SingletonEquationSolving` 具有更高的灵活性，支持修改表达式中的已知参数，但是它不安全，只能在单线程中使用哦！多线程状态建议 clone 一下！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolvingTwo;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolverExpression;
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;

@Functions("sum(x, y, z) = x + y + z")
public class MAIN {

    public static void main(String[] args) {
        if (!Mathematical_Expression.register_jvm_function(MAIN.class)) {
            System.out.println("注册函数失败");
            return;
        }
        SingletonEquationSolvingTwo instance = (SingletonEquationSolvingTwo) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving2);
        EquationSolverExpression compile = instance.compile("x + sum(1,2,3) = 0", false);
        compile.setUseNewton(false);
        compile.setBisectionLeft(-100);
        compile.setBisectionRight(100);
        CalculationNumberResults calculation1 = compile.calculation(false);
        System.out.println(calculation1.getResult());
        // 获取到编译时候包装在内部的表达式
        JvmExpression jvmExpression = compile.getJvmExpression();
        // 我们可以直接修改其中的一些参数
        // 这里的使用方法和JvmExpression中的方法一样
        // 但是请注意 TODO 索引0的参数是x 您在这里可以修改 但是可能会在您调用求解器的时候被重置，因此不建议您修改 x 的值
        jvmExpression.setParamNumber(1, 10);
        // 推荐您调用 实现安全的修改！
        compile.setKnownNumber(1, 10);
        System.out.println(compile.getExpressionStr());
        // 这里就是调用求解器了 这里会重置索引 0 的参数！
        CalculationNumberResults calculation2 = compile.calculation(false);
        System.out.println(calculation2.getResult());
        // TODO 请注意 SingletonEquationSolvingTwo 是单线程的，我们提供了一个克隆方法 使用这个方法克隆是轻量级的！
        EquationSolverExpression clone = compile.cloneExpression();
        clone.setKnownNumber(1, 100);
        System.out.println(clone.getExpressionStr());
        System.out.println(clone.calculation(false).getResult());
    }
}
```