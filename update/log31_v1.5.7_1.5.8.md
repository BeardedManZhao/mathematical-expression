# 1.5.7 -> 1.5.8 版本更新日志

### 更新时间：2026年05月09日

> 自 1.5.8 版本起，为了符合规范，Maven 仓库的 groupId 已统一更改为全小写。

==Java==

- 更新版本号为 1.5.8
- 部分组件支持了科学计数法，未来会逐渐增多支持科学计数法的组件。
```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.JvmCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;

public class MAIN {

    public static void main(String[] args) {
        JvmCalculation instance = (JvmCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.jvmCalculation);
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 在 注册函数的时候 可使用科学计数法
        Mathematical_Expression.register_jvm_function("f(x) = 1E-5 + x + 1e-5");
        // 编译表达式并计算
        // 注意 在 JvmCalculation 的编译操作中 科学计数法暂时还不支持
        JvmExpression compile = instance.compile("100 + f(10)", false);
        System.out.println(compile.calculation(true).getResult());

        // 修改第2个操作数为20
        compile.setParamNumber(1, 20);
        System.out.println(compile.calculation(true).getResult());
    }
}
```
- 操作数中会出现多位小数点计算时，由于不支持科学计数法带来的计算数值异常问题已修正，如以下代码

> 此问题由 [Ageds](https://github.com/Ageds) 用户提出，感谢小伙伴的支持！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;

public class MAIN {

    private static BigDecimal evaluateExpression(String expression) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            Object result = engine.eval(expression);
            return new BigDecimal(result.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO; // 返回一个默认值或抛出异常
        }
    }

    public static void main(String[] args) {
        FunctionFormulaCalculation2 instance = (FunctionFormulaCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 不启用数学精度计算，使用计算机的二进制形式存储的小数点
        Mathematical_Expression.Options.setUseBigDecimal(false);
        String formula = "(0.0867-0.0874)*3969.4687/10000 ";
        System.out.println("预期结果：" + evaluateExpression(formula));
        CalculationNumberResults calculation1 = instance.calculation(formula, true);
        System.out.println("计算结果：" + calculation1.getResult());
        System.out.println("计算结果：" + calculation1.getBigDecimalResult());
    }
}
```