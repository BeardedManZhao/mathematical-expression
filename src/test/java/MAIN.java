import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolving;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolver;

@Functions("sum(x, y, z) = x + y + z")
public class MAIN {

    public static void main(String[] args) {
        Mathematical_Expression.register_jvm_function(MAIN.class);
        SingletonEquationSolving instance = (SingletonEquationSolving) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving);
        EquationSolver compile = instance.compile("sum(x, x, x)=10", false);
        System.out.println(compile.calculation(false));
    }
}