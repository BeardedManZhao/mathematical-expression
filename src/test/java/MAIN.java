import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolvingTwo;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolverExpression;

public class MAIN {

    public static void main(String[] args) {
        final SingletonEquationSolvingTwo instance = (SingletonEquationSolvingTwo) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving2);
        EquationSolverExpression compile = instance.compile("1 + x + 4 = 10", false);
        String explain = compile.explain();
        System.out.println(explain);

        compile.setKnownNumber(0, 5);
        System.out.println(compile.explain());
    }
}