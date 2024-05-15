import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;

public class MAIN {
    public static void main(String[] args) {
        // 获取到一个有括号计算组件 您可以根据需求更换组件
        final Calculation instance = Mathematical_Expression.getInstance(
                Mathematical_Expression.bracketsCalculation2
        );
        // 然后进行一个简单的检查 这里我们要查询 0.3 * 3 的执行过程
        final String s = "0.3 * 3";
        // 在这里的计算会精度损失
        System.out.println(instance.calculation(s));
        // 启用精度模式
        Mathematical_Expression.Options.setUseBigDecimal(true);
        System.out.println(instance.calculation(s));
    }
}