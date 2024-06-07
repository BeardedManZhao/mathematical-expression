import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.bool.BooleanCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationBooleanResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions({
        "f(x, y) = x - y"
})
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(MAIN.class);
        BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("Bool");
        booleanCalculation2.setCalculation(Mathematical_Expression.functionFormulaCalculation2);
        // 创建3个表达式
        String s1 = "1 + 2 + 4 * f(10, 3)";
        String s2 = "2 + 30 + (2 * 3) - 1";
        String s3 = "1 + 3 * 10";
        extracted(booleanCalculation2, s1 + " > " + s2);// false
        extracted(booleanCalculation2, s1 + " < " + s2);// true
        extracted(booleanCalculation2, s1 + " = " + s3);// true
        extracted(booleanCalculation2, s1 + " == " + s3);// true
        extracted(booleanCalculation2, s1 + " != " + s3);// false
        extracted(booleanCalculation2, s1 + " <> " + s3);// false
        extracted(booleanCalculation2, s1 + " <= " + s3);// true
        extracted(booleanCalculation2, s1 + " >= " + s3);// true
        extracted(booleanCalculation2, s1 + " != " + s2);// true
        extracted(booleanCalculation2, s1 + " <> " + s2);// true
    }

    private static void extracted(BooleanCalculation2 booleanCalculation2, String s) throws WrongFormat {
        // 检查表达式是否有错误
        booleanCalculation2.check(s);
        // 开始计算结果
        CalculationBooleanResults calculation = booleanCalculation2.calculation(s);
        // 打印结果数值
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}