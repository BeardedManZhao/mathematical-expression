import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import top.lingyuzhao.varFormatter.core.VarFormatter;

public class MAIN {

    public static void main(String[] args) {
        // +0.0000*1
        String formula = "0.0000*1+(0.0000*0.9714+-11.3308*1.229+0.0000*1.4286+0.0008*1.4714+0.0000*1.4714+1.0000*1.4571+0.0000*1.4286+12.7365*13.3+(0.0000-0.0000)*0.0341+1.0000*1.7143+0.0000*1)/10000-0.0000*1+(0.0000*0.9714+-11.3308*1.229+0.0000*1.4286+0.0008*1.4714+0.0000*1.4714+1.0000*1.4571+0.0000*1.4286+12.7365*13.3+(0.0000-0.0000)*0.0341+1.0000*1.7143+0.0000*1)/10000";
        final BracketsCalculation2 instance = (BracketsCalculation2) Mathematical_Expression.getInstance(
                // Select the different computing components you want to use here
                Mathematical_Expression.bracketsCalculation2
        );
        LogResults explain = instance.explain(formula, true);
        explain.setNameJoin(false);
        String format = VarFormatter.MERMAID.getFormatter(true).format(explain);
        System.out.println(format);
    }
}