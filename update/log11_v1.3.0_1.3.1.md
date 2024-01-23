# 1.3.0 -> 1.3.1 版本更新日志

### 更新时间：2024年1月23日

==Java==

* 针对数学函数的注册功能，开始正式启用，允许多参函数，允许一参多用，允许函数嵌套调用，优化函数计算性能，以及数学函数表达式注册时的检查逻辑。

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.function.FunctionPackage;
import core.calculation.function.ManyToOneNumberFunction;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 将数学函数包导入 这能够将 sum 函数导入进来
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 在这里注册一个函数
        Mathematical_Expression.register_function("f(x) = x * 1 * x * sum(1, 1.5)");
        // 在这里打印出计算结果
        System.out.println(((ManyToOneNumberFunction) CalculationManagement.getFunctionByName("f")).run(20));
    }
}
```
  