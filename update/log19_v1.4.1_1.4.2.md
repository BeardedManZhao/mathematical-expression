# 1.4.1 -> 1.4.2 版本更新日志

### 更新时间：2024年05月29日

==Java==

- 优化表达式对象的类结构
- 对于缓存操作进行了优化，将缓存接入到编译操作中，让缓存效果更加明显
- 对于数学表达式的编译工作的代码进行优化，减少了一些不必要的逻辑。
- 对于 `io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2`
  计算组件，我们也提供了表达式的编译功能！
- 为编译出来的计算表达式对象新增了两个函数 `convertToMultiPrecisionSupported` 和 `isUnBigDecimal`

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.Expression;

public class MAIN {

    public static void main(String[] args) {
        // 获取到计算表达式组件
        final BracketsCalculation2 instance = (BracketsCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 将表达式 3 * (1.3 - 1) 编译为一个表达式对象
        final Expression compile = instance.compileBigDecimal("3 * (1.3 - 1)", true);
        if (!compile.isBigDecimal()) {
            // 非精度模式的计算
            System.out.println(compile.calculationCache(true));
        }
        if (compile.isBigDecimal()) {
            // 精度模式的计算
            System.out.println(compile.calculationBigDecimalsCache(false));
        }
    }
}
```

- 为 `FastSumOfIntervalsBrackets`和 `FastMultiplyOfIntervalsBrackets` 提供了表达式的编译功能！
- 优化了累乘逻辑！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastMultiplyOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastSumOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.container.NameExpression;

public class MAIN {
  public static void main(String[] args) {
    // 获取到快速区间累加表达式 以及 累乘表达式
    FastSumOfIntervalsBrackets fastSumOfIntervalsBrackets = (FastSumOfIntervalsBrackets) Mathematical_Expression.getInstance(Mathematical_Expression.fastSumOfIntervalsBrackets);
    FastMultiplyOfIntervalsBrackets fastMultiplyOfIntervalsBrackets = (FastMultiplyOfIntervalsBrackets) Mathematical_Expression.getInstance(Mathematical_Expression.fastMultiplyOfIntervalsBrackets);

    // 将 从 1 到 10 的和 编译为表达式对象
    fastSumOfIntervalsBrackets.step = 1;
    final NameExpression expression1 = fastSumOfIntervalsBrackets.compile("2-1, 10", true);
    // 查看表达式的信息
    run(expression1);

    System.out.println("---------------");

    // 将 从 1 到 10 的乘积 编译为表达式对象
    fastMultiplyOfIntervalsBrackets.step = 1;
    final NameExpression expression2 = fastMultiplyOfIntervalsBrackets.compile("2-1, 10", true);
    run(expression2);
  }

  private static void run(NameExpression expression) {
    // 查看表达式的信息
    System.out.println("表达式来源：" + expression.getCalculationName());
    System.out.println("表达式的格式：" + expression.getExpressionStr());
    System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
    System.out.println(">>> 开始为表达式对象添加多精度支持");
    expression.convertToMultiPrecisionSupported();
    System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
    System.out.println("计算结果：" + expression.calculation(false));
  }
}
```

- 为 `cumulativeCalculation` 组件新增了表达式编译的支持！
```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastMultiplyOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastSumOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.container.NameExpression;

public class MAIN {
    public static void main(String[] args) {
        // 获取到区间累加表达式
        final CompileCalculation calculation = (CompileCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.cumulativeCalculation);
        // 开始编译一个表达式
        NameExpression expression = calculation.compile("n[1,10,1] 2 * (n + 1)", true);
        // 查看表达式中的信息
        run(expression);
    }

    private static void run(NameExpression expression) {
        // 查看表达式的信息
        System.out.println("表达式来源：" + expression.getCalculationName());
        System.out.println("表达式的格式：" + expression.getExpressionStr());
        System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
        System.out.println(">>> 开始为表达式对象添加多精度支持");
        expression.convertToMultiPrecisionSupported();
        System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
        System.out.println("计算结果：" + expression.calculation(false));
    }
}
```
