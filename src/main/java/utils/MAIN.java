package utils;

import core.calculation.BracketsCalculation2;
import core.calculation.PrefixExpressionOperation;
import core.container.CalculationNumberResults;

public class MAIN {
    public static void main(String[] args) {
        BracketsCalculation2 z = BracketsCalculation2.getInstance("z");

        // TODO 处理减法和除法的时候有问题 加法和乘法没有问题
        PrefixExpressionOperation z1 = PrefixExpressionOperation.getInstance("z1");

        System.out.println(z1.calculation("20.0 * 3.0 + 10 * 10", true).getResult());
        String s = "20 + (1 * (1 + 2)) * 10";
        CalculationNumberResults calculationNumberResults = z.calculationBrackets(s, true);
        System.out.println(
                "计算来源[" + calculationNumberResults.CalculationSource() + "] " +
                        "计算层次[" + calculationNumberResults.getResultLayers() + "] " +
                        "计算结果[" + calculationNumberResults.getResult() + "]"
        );
    }
}
