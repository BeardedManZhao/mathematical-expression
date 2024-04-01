# 1.3.3 -> 1.3.4 版本更新日志

### 更新时间：2024年03月22日 【正在开发中】

==Java==

- 支持批量序列化和注册函数
- 针对函数表达式注册的函数，支持获取到函数的参数数量。

```java

import core.calculation.function.ExpressionFunction;
import exceptional.WrongFormat;

class MAIN {
    public static void main(String[] args) throws WrongFormat {
        final ExpressionFunction parse = ExpressionFunction.parse("f(a,b,c) = a + (c - b)");
        System.out.println("函数能够接收的参数数量：" + parse.getParamSize());
        // 使用函数进行计算
        System.out.println("f(10, 2, 3) = " + parse.run(10, 2, 3));
    }
}
```

- 针对 `ExpressionFunction` 函数对象，新增了 `explain` 函数，同时将其计算函数中的表达式检查逻辑移除，此逻辑会在解析函数的时候进行一次，提升了函数的计算速度！

```java
package utils;

import core.calculation.function.ExpressionFunction;
import exceptional.WrongFormat;

class MAIN {
    public static void main(String[] args) throws WrongFormat {
        final ExpressionFunction parse = ExpressionFunction.parse("f(a,b,c) = a + (c - b)");
        System.out.println("函数能够接收的参数数量：" + parse.getParamSize());
        // 使用 explain 解释一下函数接收到 10 2 3 的数学表达式是什么样的
        System.out.println("f(10, 2, 3) = " + parse.explain(10, 2, 3));
        // 使用函数进行计算下结果
        System.out.println("f(10, 2, 3) = " + parse.run(10, 2, 3));
    }
}
```

下面就是计算结果

```
函数能够接收的参数数量：3
f(10, 2, 3) = 10.0+ (3.0-2.0)
[INFO][functionFormulaCalculation2][24-04-01:06]] : No Use shared pool: functionFormulaCalculation2(10.0+ (3.0-2.0))
f(10, 2, 3) = 11.0
```