# 1.4.1 -> 1.4.2 版本更新日志

### 更新时间：2024年05月29日 【正在开发中】

==Java==

- 对于缓存操作进行了优化，将缓存接入到编译操作中，让缓存效果更加明显
- 对于数学表达式的编译工作的代码进行优化，减少了一些不必要的逻辑。
- 对于 `io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2`
  计算组件，我们也提供了表达式的编译功能！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.Expression;

public class MAIN {

    public static void main(String[] args) {
        // 获取到计算表达式组件
        final BracketsCalculation2 instance = (BracketsCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 将表达式 3 * (1.3 - 1) 编译为一个表达式对象
        final Expression compile = instance.compileBigDecimal("3 * (1.3 - 1)", true);
        if (!compile.isBigDecimal()) {
            // 非精度模式的计算
            System.out.println(compile.calculationCache(true));
        }
        if (compile.isBigDecimal()) {
            // 精度模式的计算
            System.out.println(compile.calculationBigDecimalsCache(false));
        }
    }
}
```