package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        Mathematical_Expression.register_function("f(x) = x!");
        // 准备要计算的表达式 在这里我们将通过函数计算的 4 的阶乘 以及操作符计算的 3 的阶乘使用了起来
        final String data = "1 + f(4) + (2 - 3!)";
        // 获取到计算组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 检查与计算
        instance.check(data);
        System.out.println(instance.calculation(data));
    }
}