# 1.4.4 -> 1.4.5 版本更新日志

### 更新时间：2024年06月22日

==Java==

- 更新版本号为 1.4.5
- 新增了复数表达式对象 以及 复数计算组件。

```java
import io.github.beardedManZhao.algorithmStar.operands.ComplexNumber;
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.ComplexCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationComplexResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.ComplexExpression;
import io.github.beardedManZhao.mathematicalExpression.core.container.FunctionExpression;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions({
        "f(x, y) = x - y"
})
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(MAIN.class);
        // 将一个复数编译为计算表达式对象
        final ComplexCalculation instance = (ComplexCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.complexCalculation);
        final String s = "3 * 2 - 1 + 2*3 + f(10, 5)";
        instance.check(s);
        final ComplexExpression compile = instance.compile(s, true);

        // 我们还可以直接获取到复数的实部 和 虚部的表达式对象！
        final FunctionExpression real = compile.getFunctionExpression1();
        final FunctionExpression imaginary = compile.getFunctionExpression2();
        System.out.println(real.getExpressionStr());
        System.out.println(imaginary.getExpressionStr());

        // 直接计算出复数的结果
        final CalculationComplexResults calculation = compile.calculationCache(false);
        // 查看结果
        System.out.println(compile);
        System.out.println(calculation);
        // 获取到复数对象
        final ComplexNumber complexNumber = calculation.toComplexNumber();
        // 直接 使用科学计算库 参与共轭计算
        final ComplexNumber conjugate = complexNumber.conjugate();
        System.out.println(conjugate);
        // 还可以参与加法等运算 在这里是 自己 + 自己
        final ComplexNumber add = complexNumber.add(conjugate);
        System.out.println(add);

    }
}
```

- 为复数计算组件增加了 `explain` 支持！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.ComplexCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

@Functions({
        "f(x, y) = x - y"
})
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(MAIN.class);
        // 将一个复数编译为计算表达式对象
        final ComplexCalculation instance = (ComplexCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.complexCalculation);
        final String s = "(3 * 2 - 1) + 2*3 + f(10, 5) + f(20, 5)i";
        final LogResults explain = instance.explain(s, true);
        System.out.println(explain.explain(VarFormatter.MERMAID));
    }
}
```

```mermaid
graph TB

start1719024852277("(3 * 2 - 1) + 2*3 + f(10, 5) + f(20, 5)i")
start1719024852277==Map>Map==>start1719024852271
start1719024852271("(3 * 2 - 1) ")
start1719024852271==Map>Map==>f_1140554332
f_1140554332("(3 * 2 - 1) ")
f_1140554332==Map>Map==>f_1828214189
f_1828214189("3 * 2 - 1")
f_1828214189==Map>Map==>f_48400287
f_48400287("3*2-1")
f_48400287==Map>Map==>f_48400287_优先

f_48400287_优先==Map>Map==>f_1480426963_计算
f_1480426963("3.0 * 2.0")
f_1480426963_计算==Map>String/Number==>f_1480426963
f_1480426963--Map>value-->f_1480426963v{"6.0"}
f_1480426963_计算==Map>Map==>f_48400287_最终

f_48400287_最终==Map>Map==>f_355800472_计算
f_355800472("6.0 - 1.0")
f_355800472_计算==Map>String/Number==>f_355800472
f_355800472--Map>value-->f_355800472v{"5.0"}
f_355800472_计算==Map>String/Number==>result
result--Map>value-->resultv{"5.0"}
f_48400287==Map>Map==>f_48400287_最终

f_48400287_最终==Map>Map==>f_355800472_计算
f_355800472("6.0 - 1.0")
f_355800472_计算==Map>String/Number==>f_355800472
f_355800472--Map>value-->f_355800472v{"5.0"}
f_355800472_计算==Map>String/Number==>result
result--Map>value-->resultv{"5.0"}
f_1140554332==Map>Map==>f_52407
f_52407("5.0")
f_52407==Map>Map==>f_52407_优先

f_52407_优先==Map>Map==>f_52407_最终

f_52407_最终==Map>String/Number==>result
result--Map>value-->resultv{"5.0"}
f_52407==Map>Map==>f_52407_最终

f_52407_最终==Map>String/Number==>result
result--Map>value-->resultv{"5.0"}
start1719024852277==Map>Map==>start1719024852272
start1719024852272("+ 2*3 + f(10, 5) + f(20, 5)")
start1719024852272==Map>Map==>f

f==Map>Map==>f21_26
f21_26("f(20, 5)")
f21_26_r("f(20.0,5.0,)")
f21_26==Map>String/Number==>f21_26_r
f21_26_r--Map>value-->f21_26_rv{"15.0"}
f==Map>Map==>f10_15
f10_15("f(10, 5)")
f10_15_r("f(10.0,5.0,)")
f10_15==Map>String/Number==>f10_15_r
f10_15_r--Map>value-->f10_15_rv{"5.0"}
start1719024852272==Map>Map==>f_921775795
f_921775795("+ 2*3 + 5.0 + 15.0")
f_921775795==Map>Map==>f_-407602827
f_-407602827("+2*3+5.0+15.0")
f_-407602827==Map>Map==>f_-407602827_优先

f_-407602827_优先==Map>Map==>f_-1007084909_计算
f_-1007084909("2.0 * 3.0")
f_-1007084909_计算==Map>String/Number==>f_-1007084909
f_-1007084909--Map>value-->f_-1007084909v{"6.0"}
f_-1007084909_计算==Map>Map==>f_353957274_计算
f_353957274("6.0 + 5.0")
f_353957274_计算==Map>String/Number==>f_353957274
f_353957274--Map>value-->f_353957274v{"11.0"}
f_353957274_计算==Map>Map==>f_-407602827_最终

f_-407602827_最终==Map>Map==>f_1213925305_计算
f_1213925305("11.0 + 15.0")
f_1213925305_计算==Map>String/Number==>f_1213925305
f_1213925305--Map>value-->f_1213925305v{"26.0"}
f_1213925305_计算==Map>String/Number==>result
result--Map>value-->resultv{"26.0"}
f_-407602827==Map>Map==>f_-407602827_最终

f_-407602827_最终==Map>Map==>f_1213925305_计算
f_1213925305("11.0 + 15.0")
f_1213925305_计算==Map>String/Number==>f_1213925305
f_1213925305--Map>value-->f_1213925305v{"26.0"}
f_1213925305_计算==Map>String/Number==>result
result--Map>value-->resultv{"26.0"}
start1719024852277==Map>String/Number==>result
result--Map>value-->resultv{"5.0 + 26.0i"}
```