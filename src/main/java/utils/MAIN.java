package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 注册 f 函数
        if (Mathematical_Expression.register_function("f(x) = x + 1")) {
            // 使用 f 函数
            final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
            final CalculationResults calculation = instance.calculation("f(3)");
            System.out.println(calculation);
            // 计算一个包含 f(3) 的公式
            // 这里由于 f(3) 会被多次调用 所以会使用多次共享缓冲池
            instance.check("f(1 + 2) + f(3)");
            System.out.println(instance.calculation("f(1 + 2) + f(3)"));
        }
    }
}