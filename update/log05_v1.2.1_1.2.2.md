# 1.2 -> 1.2.1 版本更新日志

### 该版本正在迭代中

==Java==
<hr>

* Optimize the check and format logic between symbols to reduce unnecessary operations

<hr>

* 优化符号之间的检查与格式化逻辑，减少不必要的操作
* 修正区间累加计算时发生报错的情况
* 修正 FunctionFormulaCalculation 计算组件中的表达式检查逻辑。
* 能够直接通过 Mathematical_Expression 类获取到所有的计算组件以及完成函数的注销与注册。
```java
import core.Mathematical_Expression;
import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.NumberCalculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 使用门户类获取到一个函数表达式计算组件
        NumberCalculation fun = Mathematical_Expression.getInstance(
                Mathematical_Expression.FunctionFormulaCalculation2,
                "fun"
        );
        // 实现一个 sum 函数 注册到管理者中
        ManyToOneNumberFunction sum = new ManyToOneNumberFunction("sum") {
            @Override
            public double run(double... numbers) {
                double res = 0;
                for (double number : numbers) {
                    res += number;
                }
                return res;
            }
        };
        boolean isOk = Mathematical_Expression.register_function(sum);
        if (isOk) {
            // 生成一个需要被计算的函数数学表达式
            String s = "1 + 2 + sum(10, 20, 30) * 2";
            // 使用函数表达式计算组件检查与计算表达式
            fun.check(s);
            CalculationNumberResults calculation = fun.calculation(s);
            // 打印结果数据
            System.out.println(calculation.getResult());
            // 取消函数的注册
            Mathematical_Expression.unregister_function(sum);
        }
    }
}`
```
<hr>