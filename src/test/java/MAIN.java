import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastMultiplyOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FastSumOfIntervalsBrackets;
import io.github.beardedManZhao.mathematicalExpression.core.container.NameExpression;

public class MAIN {
    public static void main(String[] args) {
        // 获取到区间累加表达式
        final CompileCalculation calculation = (CompileCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.cumulativeCalculation);
        // 开始编译一个表达式
        NameExpression expression = calculation.compile("n[1,10,1] 2 * (n + 1)", true);
        // 查看表达式中的信息
        run(expression);
    }

    private static void run(NameExpression expression) {
        // 查看表达式的信息
        System.out.println("表达式来源：" + expression.getCalculationName());
        System.out.println("表达式的格式：" + expression.getExpressionStr());
        System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
        System.out.println(">>> 开始为表达式对象添加多精度支持");
        expression.convertToMultiPrecisionSupported();
        System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
        System.out.println("计算结果：" + expression.calculation(false));
    }
}