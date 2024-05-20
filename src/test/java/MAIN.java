import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.io.IOException;

public class MAIN {

    public static void main(String[] args) throws WrongFormat, IOException {
        Mathematical_Expression.register_function("f(x) = x + 1");
        Mathematical_Expression.register_function("ff(x) = x!");
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        final CalculationResults calculation = instance.explain("1 + f(3) + f(4) + f(2 + 3) + ff(4) + 2", true);
        System.out.println(calculation.explain(VarFormatter.MERMAID));
    }
}