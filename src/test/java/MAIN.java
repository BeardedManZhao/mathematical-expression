import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.PrefixExpressionOperation;
import io.github.beardedManZhao.mathematicalExpression.core.container.Expression;

public class MAIN {

    public static void main(String[] args) {
        // 获取到计算表达式组件
        final PrefixExpressionOperation instance = (PrefixExpressionOperation) Mathematical_Expression.getInstance(Mathematical_Expression.prefixExpressionOperation);
        // 将表达式 3*0.3 编译为一个表达式对象，我们在 1.4.1 版本中新增了compile & compileBigDecimal 方法，他们可以将表达式编译为对象，方便我们进行使用。
        final Expression compile = instance.compileBigDecimal("3 * 0.3", true);
        // 获取到计算结果 在这里有一个参数，设置为 false 性能会好些！设置为 true 功能会多些
        System.out.println(compile.calculation(true));
        // 我们可以使用这个表达式重复的计算，对于表达式对象来说 多次调用 calculationCache 的效率会很高
        // 除了第一次 calculationCache，其余调用 calculationCache 的复杂度皆为 O(1)
        System.out.println(compile.calculationCache(false));
        // 值得注意的是，在编译对象中，我们还提供了一个 calculationBigDecimalsCache 函数
        // 当我们在编译的时候 如果是使用 compile 编译出来的就需要使用 calculationCache 计算
        // 如果是使用 compileBigDecimal 编译出来的就需要使用 calculationBigDecimalsCache 计算
        if (compile.isBigDecimal()) {
            // 我们可以使用 isBigDecimal 函数来判断它是什么类型的计算模式
            System.out.println(compile.calculationBigDecimalsCache(false));
        }
    }
}