import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions("sum(x, y) = x + y")
public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_jvm_function(MAIN.class);
        Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.jvmCalculation);
        instance.check("11+sum(1, 23)*3");
        System.out.println(instance.calculation("11+sum(1, 23)*3"));
    }
}