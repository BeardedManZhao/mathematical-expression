package utils;

import core.calculation.BracketsCalculation;
import core.calculation.BracketsCalculation2;
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
                "计算来源[" + calculationNumberResults.CalculationSource() + "] " +
                        "计算层次[" + calculationNumberResults.getResultLayers() + "] " +
                        "计算结果[" + calculationNumberResults.getResult() + "]"
        );
    }
}
