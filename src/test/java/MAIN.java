import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.JvmCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;

@Functions("sum(x, y, z) = x + y + z")
public class MAIN {

    public static void main(String[] args) {
        Mathematical_Expression.register_jvm_function(MAIN.class);
        JvmCalculation instance = (JvmCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.jvmCalculation);
        JvmExpression compile = instance.compile("1+1 + sum(1,2,3) - 2 * 3", false);
        System.out.println(compile.calculation(false));
    }
}