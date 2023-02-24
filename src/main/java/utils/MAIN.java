package utils;

import core.Mathematical_Expression;
import core.calculation.number.NumberCalculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 构建需要计算的两种表达式
        String s1 = "1 + 20 - 2 + 4", s2 = "1 + 20 - (2 + 4)";
        // 通过库获取到计算无括号表达式的计算组件
        NumberCalculation prefixExpressionOperation = Mathematical_Expression.getInstance(
                Mathematical_Expression.prefixExpressionOperation, "prefixExpressionOperation"
        );
        // 通过库获取到计算有括号表达式的计算组件
        NumberCalculation bracketsCalculation2 = Mathematical_Expression.getInstance(
                Mathematical_Expression.bracketsCalculation2, "bracketsCalculation2"
        );
        // 将第一个公式传递给无括号表达式的计算组件
        prefixExpressionOperation.check(s1);
        CalculationNumberResults calculation1 = prefixExpressionOperation.calculation(s1);
        // 打印出第一个表达式的计算结果
        System.out.println("计算层数：" + calculation1.getResultLayers() + "\n计算结果：" + calculation1.getResult() +
                "\n计算来源：" + calculation1.getCalculationSourceName());


        // 将第二个公式传递给无括号表达式的计算组件
        bracketsCalculation2.check(s2);
        CalculationNumberResults calculation2 = bracketsCalculation2.calculation(s2);
        // 打印出第二个表达式的计算结果
        System.out.println("计算层数：" + calculation2.getResultLayers() + "\n计算结果：" + calculation2.getResult() +
                "\n计算来源：" + calculation2.getCalculationSourceName());
    }
}