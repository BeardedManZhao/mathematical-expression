# 1.2.1 -> 1.2.2 版本更新日志

### 更新时间：UTC2023-02-25 09:40

==Java==
<hr>

* For Mathematical_ The Expression class undergoes structural refactoring and optimization to improve its usage performance, while enabling it to achieve fully consistent invocation syntax with Python, reducing learning difficulty.

<hr>

* 为 Mathematical_Expression 类进行结构重构与优化，提升其使用性能，同时使其能够实现与Python完全一致的调用语法，降低学习难度。

```java
package utils;


import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println("这是一个示例代码样本！");
        // Build two expressions to be evaluated
        String s1 = "1 + 20 - 2 + 4", s2 = "1 + 20 - (2 + 4)";
        // Obtaining an expression evaluation component without parentheses through the library
        Calculation prefixExpressionOperation = Mathematical_Expression.prefixExpressionOperation.getInstance("prefixExpressionOperation");
        // Obtain the bracketed expression calculation component through the library
        Calculation bracketsCalculation2 = Mathematical_Expression.bracketsCalculation2.getInstance("bracketsCalculation2");
        // Another way is to get the calculation component object, which is more similar to the writing method in Java
        // bracketsCalculation2 = Mathematical_Expression.getInstance(Mathematical_Expression.booleanCalculation2, "bracketsCalculation2");

        // Pass the first formula to the parenthesis expression calculation component Check and calculate the formula can also be passed to the parenthesis expression calculation
        prefixExpressionOperation.check(s1);
        CalculationNumberResults calculation1 = (CalculationNumberResults) prefixExpressionOperation.calculation(s1);
        // Print the calculation result of the first expression
        System.out.println("计算层数：" + calculation1.getResultLayers() + "\n计算结果：" + calculation1.getResult() +
                "\n计算来源：" + calculation1.getCalculationSourceName());

        // Pass the second formula to the bracketed expression calculation component for check and calculation
        bracketsCalculation2.check(s2);
        CalculationNumberResults calculation2 = (CalculationNumberResults) bracketsCalculation2.calculation(s2);
        // Print the calculation result of the first expression
        System.out.println("计算层数：" + calculation2.getResultLayers() + "\n计算结果：" + calculation2.getResult() +
                "\n计算来源：" + calculation2.getCalculationSourceName());
    }
}
```
<hr>
