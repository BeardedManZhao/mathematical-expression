package utils;

import core.Mathematical_Expression;
import core.calculation.function.FunctionPackage;
import core.calculation.function.ManyToOneNumberFunction;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        Mathematical_Expression.register_function("f(x) = x * 1 * x * sum(1, 1.5)");
        System.out.println(((ManyToOneNumberFunction) CalculationManagement.getFunctionByName("f")).run(20));
    }
}