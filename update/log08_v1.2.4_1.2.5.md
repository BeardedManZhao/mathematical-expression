# 1.2.4 -> 1.2.5 版本更新日志

### 更新时间：2024年1月8日

==Java==

* 提供了内置函数包，您可以直接通过内置的函数包实现一些函数的注册操作，对于函数包中已有函数，则不需要手动实现!

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import core.container.CalculationResults;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println(ConstantRegion.VERSION);
        // 获取到函数计算组件
        final Calculation calculation = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 构建需要计算的表达式 TODO 在这里我们使用了一个叫做 avg 的函数 代表求均值
        final String s = "2 * avg(1, 2, 3, 10)";
        // 注册 Math 函数包到库 Math 函数包中有 avg 函数的内置实现
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 开始进行检查
        calculation.check(s);
        // 开始进行计算
        final CalculationResults results = calculation.calculation(s);
        System.out.println("计算结果：" + results);
    }
}
```
