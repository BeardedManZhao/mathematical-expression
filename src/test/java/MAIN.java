import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationResults;
import top.lingyuzhao.varFormatter.core.VarFormatter;

public class MAIN {

    public static void main(String[] args) {
        FunctionFormulaCalculation2 instance = (FunctionFormulaCalculation2) Mathematical_Expression.getInstance(
                Mathematical_Expression.functionFormulaCalculation2
        );
        Mathematical_Expression.register_function(FunctionPackage.MATH);

        // 可视化计算
        CalculationResults calculation = instance.explain("1 + sum( 1 + sum( 1 - 1, 10) )", true);
        System.out.println(calculation.explain(VarFormatter.MERMAID));
        // 数值计算
        CalculationNumberResults calculation1 = instance.calculation("1 + sum( 1 + sum( 1 - 1, 10) )", true);
        System.out.println("结果：" + calculation1.getResult());
    }
}