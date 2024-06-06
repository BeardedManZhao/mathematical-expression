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