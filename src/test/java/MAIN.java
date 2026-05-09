import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;

public class MAIN {

    private static BigDecimal evaluateExpression(String expression) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            Object result = engine.eval(expression);
            return new BigDecimal(result.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO; // 返回一个默认值或抛出异常
        }
    }

    public static void main(String[] args) {
        FunctionFormulaCalculation2 instance = (FunctionFormulaCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 不启用数学精度计算，使用计算机的二进制形式存储的小数点
        Mathematical_Expression.Options.setUseBigDecimal(false);
        String formula = "(0.0867-0.0874)*3969.4687/10000 ";
        System.out.println("预期结果：" + evaluateExpression(formula));
        CalculationNumberResults calculation1 = instance.calculation(formula, true);
        System.out.println("计算结果：" + calculation1.getResult());
        System.out.println("计算结果：" + calculation1.getBigDecimalResult());
    }
}