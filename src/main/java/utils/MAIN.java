package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation2;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Implement a sum function
        ManyToOneNumberFunction manyToOneNumberFunction = new ManyToOneNumberFunction("sum") {
            @Override
            public double run(double... numbers) {
                double res = 0;
                for (double number : numbers) {
                    res += number;
                }
                return res;
            }
        };
        // Register this function to the administrator
        CalculationManagement.register(manyToOneNumberFunction);
        // Get the new version of function calculation component
        FunctionFormulaCalculation2 functionFormulaCalculation2 = FunctionFormulaCalculation2.getInstance("zhao");
        // Build the formula we need to calculate
        // TODO The function sum parameter in this expression is not only one, but also a multi parameter function
        String s = "2 * (200 - sum(1 + 10.1024, 2, 3)) + sum(10, 20)";
        // Enabling the shared pool can speed up the calculation. The more complex the calculation formula is, the more significant the effect of the shared pool is
        functionFormulaCalculation2.setStartSharedPool(true);
        // Check the formula for errors
        functionFormulaCalculation2.check(s);
        // Get the calculation results
        CalculationNumberResults calculation = functionFormulaCalculation2.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
        System.err.println(2 * (200 - (1 + 10.1024 + 2 + 3)) + (10 + 20));
    }
}