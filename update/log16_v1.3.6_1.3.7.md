# 1.3.6 -> 1.3.7 版本更新日志

### 更新时间：2024年05月15日 【稳定版本发布】

==Java==

- `Mathematical_Expression.functionFormulaCalculation2` 组件支持了 `explain`;

- ⚠️ 请注意，我们将在 1.4.0 版本以及之后的所有版本中 重构包名为 `io.github.beardedManZhao.mathematicalExpression` 这是为了避免在
  Java 的诸多依赖中，包名出现冲突的情况~

```java
import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.LogResults;
import exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        // 将 f 函数注册进来
        Mathematical_Expression.register_function("f(x) = x * x");
        Mathematical_Expression.register_function("ff(x) = x + 1");
        // 准备要计算的表达式
        String data = "1 + f(20) + (ff(10 - 2) + f(20 - 2 * 3))";
        // 获取计算器
        Calculation calculation = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 将计算过程绘制出来
        final LogResults explain = calculation.explain(data, true);
        System.out.println(explain.explain(VarFormatter.MERMAID));
    }
}
```
