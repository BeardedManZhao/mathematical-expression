# 1.3.5 -> 1.3.6 版本更新日志

### 更新时间：2024年05月14日 【此版本开发完毕】

==Java==

- `Mathematical_Expression.cumulativeCalculation` 组件支持了 `explain`;
- 为整个项目添加了一个设置模块，允许用户设置一些参数！
- 为所有的数值解析操作添加了缓存功能，并将缓存设置项目集成到配置模块中，减少重复的数值解析。
- 为所有的计算操作添加了针对 `BigDecimal` 的支持，它允许用户以大精度的计算模式来执行计算，这将会减少计算结果的误差。
- 为 `CalculationNumberResults` 添加了 `getBigDecimalResult` 的支持。
-

```java
import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationResults;

import java.math.BigDecimal;

/**
 * This is the main entry point for the application, demonstrating mathematical expression parsing and evaluation.
 */
public class MAIN {
    public static void main(String[] args) {
        // Obtain an instance of the calculation component, which supports parentheses handling.
        final Calculation calculationInstance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // Define a sample mathematical expression to evaluate.
        final String inputExpression = "0.3 * 3";
        // Enable caching to improve performance.
        Mathematical_Expression.Options.setUseCache(true);

        // Enable BigDecimal for more accurate results.
        Mathematical_Expression.Options.setUseBigDecimal(true);
        // Evaluate the expression and print the result.
        System.out.println(calculationInstance.calculation(inputExpression));

        // Disable BigDecimal for faster performance.
        Mathematical_Expression.Options.setUseBigDecimal(false);
        // Evaluate the expression and print the result.
        final CalculationResults calculation = calculationInstance.calculation(inputExpression);
        System.out.println(calculation);

        // Can extract different numerical objects
        System.out.println("Can extract different numerical objects!");
        final double result = (double) calculation.getResult();
        final BigDecimal bigDecimalResult = calculation.getBigDecimalResult();
        System.out.println(result);
        System.out.println(bigDecimalResult);
    }
}
```
