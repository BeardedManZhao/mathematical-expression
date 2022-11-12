package utils;

import core.calculation.bool.BooleanCalculation2;
import core.calculation.number.BracketsCalculation;
import core.calculation.number.BracketsCalculation2;
import core.calculation.number.CumulativeCalculation;
import core.container.CalculationBooleanResults;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到括号表达式计算组件
        BracketsCalculation z = BracketsCalculation2.getInstance("z");
        // 构建需要我们计算的公式
        String s = "10 + (20 + (10 % 3) * 10)";
        // 检查公式是否有错误
        z.check(s);
        // 开始计算公式 并获取到一个计算结果
        CalculationNumberResults calculationNumberResults = z.calculation(s, false);
        // 打印出计算结果的信息
        System.out.println(
                "计算来源[" + calculationNumberResults.getCalculationSourceName() + "] " +
                        "计算层次[" + calculationNumberResults.getResultLayers() + "] " +
                        "计算结果[" + calculationNumberResults.getResult() + "]"
        );

        // 获取到比较运算计算组件
        BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("b");
        // 构建需要我们计算的公式
        String s1 = "1 + (10 * 10) <= (10 * 10) + (1 * 1) + 1";
        // 检查公式是否有错误
        booleanCalculation2.check(s1);
        // 开始计算公式
        CalculationBooleanResults calculation = booleanCalculation2.calculation(s1);
        // 打印计算结果
        System.out.println(calculation.getLeft() + " <= " + calculation.getRight() + " " + calculation.getResult());

        // 获取到累加运算计算组件
        CumulativeCalculation c = CumulativeCalculation.getInstance("C");
        // 构建需要我们计算的公式
        String s2 = "n[0, 10, 1] (n + n)";
        // 检查公式是否有错误
        c.check(s2);
        // 开始计算公式
        CalculationNumberResults calculation1 = c.calculation(s2);
        // 打印计算结果
        System.out.println(calculation1.getResult());
    }
}
