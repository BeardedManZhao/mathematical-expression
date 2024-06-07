# 1.4.2 -> 1.4.4 版本更新日志

### 更新时间：2024年06月07日

> 首先要感谢 [17号留言](https://github.com/BeardedManZhao/mathematical-expression/issues/17) [16号留言](https://github.com/BeardedManZhao/mathematical-expression/issues/16) 的发起者提供的建议！！！

==Java==

[//]: # (接下来为1.4.3 基本更新)

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

- 日志配置文件移除，这是为了能够将配置文件完全交给开发者去设定，避免框架配置文件覆盖了项目，优化详情可见：[17号留言](https://github.com/BeardedManZhao/mathematical-expression/issues/17)
- 将`varFormatter` 库做为必须库，而非可选库，这有效的避免一些意外情况，详细信息请查询：[16号留言](https://github.com/BeardedManZhao/mathematical-expression/issues/16)
- 优化了函数计算组件的缓存共享池原理，将其中的格式化操作进行删减，提高了计算组件的效率。
- 对于比较计算组件，提供了 `setCalculation` 函数，允许开发者自定义数值计算规则。
- 优化了比较计算组件的逻辑，加快了计算速度，删减了不必要的逻辑。

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.bool.BooleanCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationBooleanResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions({
        "f(x, y) = x - y"
})
public class MAIN {
  public static void main(String[] args) throws WrongFormat {
    Mathematical_Expression.register_function(MAIN.class);
    BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("Bool");
    booleanCalculation2.setCalculation(Mathematical_Expression.functionFormulaCalculation2);
    // 创建3个表达式
    String s1 = "1 + 2 + 4 * f(10, 3)";
    String s2 = "2 + 30 + (2 * 3) - 1";
    String s3 = "1 + 3 * 10";
    extracted(booleanCalculation2, s1 + " > " + s2);// false
    extracted(booleanCalculation2, s1 + " < " + s2);// true
    extracted(booleanCalculation2, s1 + " = " + s3);// true
    extracted(booleanCalculation2, s1 + " == " + s3);// true
    extracted(booleanCalculation2, s1 + " != " + s3);// false
    extracted(booleanCalculation2, s1 + " <> " + s3);// false
    extracted(booleanCalculation2, s1 + " <= " + s3);// true
    extracted(booleanCalculation2, s1 + " >= " + s3);// true
    extracted(booleanCalculation2, s1 + " != " + s2);// true
    extracted(booleanCalculation2, s1 + " <> " + s2);// true
  }

  private static void extracted(BooleanCalculation2 booleanCalculation2, String s) throws WrongFormat {
    // 检查表达式是否有错误
    booleanCalculation2.check(s);
    // 开始计算结果
    CalculationBooleanResults calculation = booleanCalculation2.calculation(s);
    // 打印结果数值
    System.out.println(
            "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                    "\t计算来源：" + calculation.getCalculationSourceName()
    );
  }
}
```

- 函数包更新 新增了逻辑函数 `FunctionPackage.BRANCH` 

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions({
        "f(x, y) = x - y"
})
public class MAIN {
  public static void main(String[] args) throws WrongFormat {
    Mathematical_Expression.register_function(MAIN.class);
    Mathematical_Expression.register_function(FunctionPackage.BRANCH);

    final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
    // 直接计算
    instance.check("1 + ifEq(10-2, 8, 10, 20)");
    // 如果 10-2 == 8 则返回 1 + 10 否则返回 1 + 20
    System.out.println(instance.calculation("1 + ifEq(10-2, 8, 10, 20)"));
  }
}
```

[//]: # (接下来为 1.4.4 补充更新)

- 版本号更新到 `1.4.4`
- 将不再使用到的工具类中的某些函数进行删减！
