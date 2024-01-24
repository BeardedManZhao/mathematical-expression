# 1.3.1 -> 1.3.2 版本更新日志

### 更新时间：2024年?月??日

==Java==

* 支持了新操作符 `!` 这代表的就是阶乘的意思，例如 `x!` 或者是 `2!` 且此操作符可以在自定义函数中使用，下面是一个示例

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        Mathematical_Expression.register_function("f(x) = x!");
        // 准备要计算的表达式 在这里我们将通过函数计算的 4 的阶乘 以及操作符计算的 3 的阶乘使用了起来
        final String data = "1 + f(4) + (2 - 3!)";
        // 获取到计算组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 检查与计算
        instance.check(data);
        System.out.println(instance.calculation(data));
    }
}
```

* 针对诸多的 分支判断以及检查操作使用了性能更好的处理算法
* 在函数包 `FunctionPackage.MATH` 中 新增了阶乘函数 `factorial`
* 针对函数计算组件的共享池使用 hash 缓冲表进行存储，实现更加广泛的缓存操作