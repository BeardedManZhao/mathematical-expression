// Import necessary classes and packages for mathematical calculations and functions

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

// Define a class 'MAIN' with a function that calculates the factorial of x plus 1
@Functions("f(x) = x! + 1")
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Import built-in functions like 'sum' for use
        // Register the math function library
        Mathematical_Expression.register_function(FunctionPackage.MATH);

        // Alternatively, register custom functions, e.g., a function adding two numbers
        Mathematical_Expression.register_function("fTwo(x, y) = x + y");

        // Register all annotated functions in the 'MAIN' class for use
        Mathematical_Expression.register_function(MAIN.class);

        // Initialize the calculation component
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);

        // Perform a simple check on the expression
        instance.check("1 + sum(1,2,3,4) + f(3) * fTwo(1, 2)");

        // Calculate the expression, which can include functions
        final CalculationResults calculation = instance.calculation("1 + sum(1,2,3,4) + f(3) * fTwo(1, 2)");

        // Print the result
        System.out.println(calculation.getResult());
    }
}