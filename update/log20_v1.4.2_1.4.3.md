# 1.4.2 -> 1.4.3 版本更新日志

### 更新时间：2024年06月03日【正在开发中...】

==Java==

- 为函数计算表达式组件增加了编译支持！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.FunctionExpression;

public class MAIN {
    public static void main(String[] args) {
        // 注册数学函数包
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 获取到区间累加表达式
        final FunctionFormulaCalculation2 calculation = (FunctionFormulaCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 开始编译一个表达式
        FunctionExpression expression = calculation.compile("1 + sum(1+2+3, 4-(10-9)) + avg(20, 40) + avg(20, 40)", true);
        // 查看表达式中的信息
        run(expression);
    }

    private static void run(FunctionExpression expression) {
        // 查看表达式的信息
        System.out.println("表达式来源：" + expression.getCalculationName());
        System.out.println("表达式的格式：" + expression.getExpressionStr());
        System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
        System.out.println("表达式中使用到的函数：" + expression.getFunNames());
        System.out.println("计算结果：" + expression.calculation(true));
        System.out.println(">>> 开始将其中的第二个 avg 函数修改为 sum");
        // 第二个 avg 函数位于第2索引位置，因此在这本质上是将第二个avg函数修改为sum函数
        expression.setFunName(2, "sum");
        System.out.println("表达式的格式：" + expression.getExpressionStr());
        System.out.println("表达式中使用到的函数：" + expression.getFunNames());
        System.out.println("计算结果：" + expression.calculation(false));
    }
}
```