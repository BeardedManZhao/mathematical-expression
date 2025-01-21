# 1.4.7 -> 1.4.7.20 版本更新日志

### 更新时间：2025年01月21日

> 这是一个试验版本

==Java==

- 更新版本号为 1.4.7.20
- 
- 为 `setUseBigDecimal(true)` 它会作用在每个计算组件中，有望提升部分组件的灵活性，感谢 https://github.com/BeardedManZhao/mathematical-expression/issues/20 提出者的贡献!

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationResults;

public class MAIN {
    public static void main(String[] args) {
        // 获取到一个有括号计算组件 您可以根据需求更换组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 如果您没有单独为 instance 做计算模式的设置，则 它使用的是 Mathematical_Expression.Options 的配置
        System.out.println(instance.isBigDecimal());
        Mathematical_Expression.Options.setUseBigDecimal(true);
        System.out.println(instance.isBigDecimal());
        // 您可以使用这个 instance.setBigDecimal(true); 来单独指定计算组件使用的计算模式
        instance.setBigDecimal(false);
        // 这样之后 计算模式将覆盖掉 Mathematical_Expression.Options.setUseBigDecimal(true); 不会受到约束 由计算组件单独管理
        // TODO 值得注意的是 此函数的修改只能作用在计算组件的引用层，不会作用在计算组件的单例子组件上（单例在内存中是唯一且只读的，这有利于性能和内存优化），因此 更推荐使用全局配置，全局配置可以有效的作用到更底层
        System.out.println(instance.isBigDecimal());
        CalculationResults calculation = instance.calculation("0.333333333333*3");
        System.out.println(calculation.getResult());
    }
}
```