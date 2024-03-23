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