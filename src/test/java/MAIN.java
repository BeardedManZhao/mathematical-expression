
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

// 准备一个数学函数 x 的阶乘 + 1
@Functions("f(x) = x! + 1")
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 将 MAIN 注解的函数注册 并进行使用
        Mathematical_Expression.register_function(MAIN.class);
        final Calculation instance = Mathematical_Expression.getInstance(
                // 在这里选择函数计算组件即可
                Mathematical_Expression.functionFormulaCalculation2
        );
        // 如果您确保表达式的无误，可以不检查
        instance.check("f(1 + 2) - 3");
        System.out.println(instance.calculation("f(1 + 2) - 3"));
        System.out.println(instance.explain("f(1 + 2) - 3", true).explain(VarFormatter.MERMAID));
    }
}