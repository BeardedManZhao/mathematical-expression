# 1.2.5 -> 1.2.6 版本更新日志

### 更新时间：2024年1月12日

==Java==

* 针对函数的注册操作，您可以直接传递一个函数的数学表达式实现注册，可以不通过实现函数接口的方式注册，更加方便，下面就是一个示例。
    * 此版本中的函数注册操作，您的每个形参只能使用一次，此版本中的函数计算功能正在实验中，在1.3.1版本中，函数功能将会完全完善。

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.container.CalculationResults;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println(ConstantRegion.VERSION);
        // 获取到函数计算组件
        final Calculation calculation = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 开始进行注册 TODO 我们在这里注册了一个叫做 mySum 的函数 它接收两个参数 输出的是两个参数的求和结果
        if (!Mathematical_Expression.register_function("mySum(a, b) = a + b")) {
            System.out.println("函数注册失败!");
            return;
        }
        // 构建需要计算的表达式 TODO 在这里我们使用了 mySum 的函数 并传递了两个参数
        final String s = "2 * mySum(1 + 7, 2)";
        // 开始进行检查和计算
        calculation.check(s);
        final CalculationResults results = calculation.calculation(s);
        // 打印结果：CalculationNumberResults{result=20.0, source='BracketsCalculation2'} 其中的 result 为 20 这就是我们计算的结果
        System.out.println(results);
        // 也可以直接进行结果提取
        System.out.println(s + " = " + ((CalculationNumberResults) results).getResult());
    }
}
```

下面就是计算的结果

```
CalculationNumberResults{result=20.0, source='BracketsCalculation2'}
2 * mySum(1 + 7, 2) = 20.0

```

您可以采用下面这种简洁的写法

```java
package utils;

import core.Mathematical_Expression;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println(ConstantRegion.VERSION);
        // 开始进行注册 TODO 我们在这里注册了一个叫做 mySum 的函数 它接收两个参数 输出的是两个参数的求和结果
        if (Mathematical_Expression.register_function("mySum(a, b) = a + b")) {
            System.out.println("函数注册成功!");
        }
    }
}
```