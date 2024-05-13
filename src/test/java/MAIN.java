import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.LogResults;
import exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

/**
 * This is the main entry point for the application, demonstrating mathematical expression parsing and evaluation.
 */
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Obtain an instance of the calculation component, which supports parentheses handling.
        final Calculation calculationInstance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);

        // Define a sample mathematical expression to evaluate.
        final String inputExpression = "1 + 2 ^ (2 + (10 - 7)) * 3 + 2";

        // Check the input expression for correct formatting.
        calculationInstance.check(inputExpression);

        // Explain the execution process, which returns a log object containing the result.
        final LogResults explanation = calculationInstance.explain(inputExpression, true);
        System.out.println("计算结果: " + explanation.getResult());

        // The LogResults object is primarily used for visualizing the execution flow.
        // Disable name joining when outputting the visualization, as multiple variables need to be associated.
        explanation.setNameJoin(false);

        // Format the visualization using the VarFormatter in MERMAID syntax.
        System.out.println("graph LR");
        System.out.println(explanation.explain(VarFormatter.MERMAID));
    }
}