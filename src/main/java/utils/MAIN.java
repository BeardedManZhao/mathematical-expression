package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.Functions;
import exceptional.WrongFormat;

@Functions({
        // 这里是需要被注册的两个函数 在这里标记一下
        "f(x) = x * x",
        "ff(x) = f(x) + 1"
})
public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        // 将 MAIN 类中标记的所有函数注册
        if (Mathematical_Expression.register_function(MAIN.class)) {
            // 构建需要计算的表达式
            final String string = "1 + ff(1 + 2) * 2";
            // 获取到函数计算组件
            Calculation calculation = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
            // 开始进行计算
            calculation.check(string);
            System.out.println(calculation.calculation(string));
        }
    }
}