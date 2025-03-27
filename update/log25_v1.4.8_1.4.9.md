# 1.4.8 -> 1.4.9 版本更新日志

### 更新时间：2025年02月28日

==Java==

- 更新版本号为 1.4.9
- 修复括号表达式组件 以及 无括号表达式计算组件 对于高精度计算模式判断错误的问题

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;

public class MAIN {

    public static void main(String[] args) {
        FunctionFormulaCalculation2 instance = (FunctionFormulaCalculation2) Mathematical_Expression.getInstance(
                Mathematical_Expression.functionFormulaCalculation2
        );
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 这里的判断 对于括号表达式 和 无括号表达式 有些问题，对这两组件，他们的高精度和非高精度弄反了 我们在 1.4.9 修复了此问题
        Mathematical_Expression.Options.setUseBigDecimal(false);
        // TODO 由于模式弄反了 因此使用了高精度计算 这里会有无限小数位 导致报错
        CalculationNumberResults calculation = instance.calculation("2.00 / 3.00");
        System.out.println(calculation.getResult());
    }
}
```