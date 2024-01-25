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
* 针对数学函数，您可以直接进行序列化操作，这能够有效的将已经编译好的函数对象在各种环境下进行传递，例如使用 网络 将函数对象传递给另一台计算机。

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import core.calculation.function.ManyToOneNumberFunction;
import core.container.CalculationResults;
import exceptional.WrongFormat;

import java.io.File;
import java.io.IOException;

public class MAIN {
    public static void main(String[] args) throws WrongFormat, IOException, ClassNotFoundException {
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        Mathematical_Expression.register_function("f(x, b) = x! + 1024.5 + b + (1 - 2)");

        // 查看 f 函数的公式
        show(true);


        // 将函数 f 删除 因为在这里我们要使用序列化好的文件将函数重新加载回来
        if (Mathematical_Expression.unregister_function("f")) {
            // 在这里获取到所有的函数名字 并判断其中是否有 f 函数的名字
            System.out.println("删除完毕，f 函数是否存在？" + Mathematical_Expression.getFunctionMap().contains("f"));
        }


        // 将函数 f 的序列化文件重新加载回来
        if (Mathematical_Expression.register_function(new File("C:\\Users\\zhao\\Downloads\\f.txt"))) {
            // 代表重新加载成功了 直接打印
            show(false);
            System.out.println("加载完毕，f 函数是否存在？" + Mathematical_Expression.getFunctionMap().contains("f"));
        }

        // 尝试使用 f 函数
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        final CalculationResults calculation = instance.calculation("f(3, 4)");
        System.out.println(calculation);
    }

    private static void show(boolean needSave) throws IOException {
        // 获取到注册的 f 函数
        final ManyToOneNumberFunction f = Mathematical_Expression.getFunction("f");
        if (needSave) {
            // 直接将 f 函数对象序列化 这可以将编译好的函数直接输出到文件系统指定目录的指定文件中
            Mathematical_Expression.saveFunction(f, new File("C:\\Users\\zhao\\Downloads\\f.txt"));
        }
        // 在这里直接打印函数
        System.out.println("函数公式：" + f);
    }
}
```

* 优化缓冲池缓冲算法，针对函数表达式，缓冲池可以使用许多次

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 注册 f 函数
        if (Mathematical_Expression.register_function("f(x) = x + 1")) {
            // 使用 f 函数
            final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
            final CalculationResults calculation = instance.calculation("f(3)");
            System.out.println(calculation);
            // 计算一个包含 f(3) 的公式
            // 这里由于 f(3) 会被多次调用 所以会使用多次共享缓冲池
            instance.check("f(1 + 2) + f(3)");
            System.out.println(instance.calculation("f(1 + 2) + f(3)"));
        }
    }
}
```