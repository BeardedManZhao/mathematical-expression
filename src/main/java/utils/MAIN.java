package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Instantiate a function named DoubleValue to multiply a value by 2
        ManyToOneNumberFunction myFunction = new ManyToOneNumberFunction("DoubleValue") {
            /**
             * 函数的运行逻辑实现
             *
             * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
             * @return 这里是数据经过函数转换之后的数据
             */
            @Override
            public double run(double... numbers) {
                // Among the parameters here, the first parameter is the parameter passed in by FunctionFormulaCalculation
                return numbers[0] * 2;
            }
        };
        // Register function to manager
        CalculationManagement.register(myFunction);
        // Get a component that calculates the cumulative mathematical expression
        FunctionFormulaCalculation functionFormulaCalculation = FunctionFormulaCalculation.getInstance("zhao");
        // Build a mathematical expression that uses the function DoubleValue
        String s = "2 * DoubleValue(2 + 3) + 1";
        // Check mathematical expressions
        functionFormulaCalculation.check(s);
        // Calculation results
        CalculationNumberResults calculation = functionFormulaCalculation.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}