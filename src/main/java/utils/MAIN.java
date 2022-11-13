package utils;

import core.calculation.bool.BooleanCalculation2;
import core.container.CalculationBooleanResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取一个计算数学比较表达式的组件
        BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("Bool");
        // 创建3个表达式
        String s1 = "1 + 2 + 4 * (10 - 3)";
        String s2 = "2 + 30 + (2 * 3) - 1";
        String s3 = "1 + 3 * 10";
        extracted(booleanCalculation2, s1 + " > " + s2);// false
        extracted(booleanCalculation2, s1 + " < " + s2);// true
        extracted(booleanCalculation2, s1 + " = " + s3);// true
        extracted(booleanCalculation2, s1 + " == " + s3);// true
        extracted(booleanCalculation2, s1 + " != " + s3);// false
        extracted(booleanCalculation2, s1 + " <> " + s3);// false
        extracted(booleanCalculation2, s1 + " <= " + s3);// true
        extracted(booleanCalculation2, s1 + " >= " + s3);// true
        extracted(booleanCalculation2, s1 + " != " + s2);// true
        extracted(booleanCalculation2, s1 + " <> " + s2);// true
    }

    private static void extracted(BooleanCalculation2 booleanCalculation2, String s) throws WrongFormat {
        // 检查表达式是否有错误
        booleanCalculation2.check(s);
        // 开始计算结果
        CalculationBooleanResults calculation = booleanCalculation2.calculation(s);
        // 打印结果数值
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}


//    // 获取到括号表达式计算组件
//    BracketsCalculation z = BracketsCalculation2.getInstance("z");
//    // 构建需要我们计算的公式
//    String s = "10 + (20 + (10 % 3) * 10)";
//// 检查公式是否有错误
//        z.check(s);
//                // 开始计算公式 并获取到一个计算结果
//                CalculationNumberResults calculationNumberResults = z.calculation(s, false);
//                // 打印出计算结果的信息
//                System.out.println(
//                "计算来源[" + calculationNumberResults.getCalculationSourceName() + "] " +
//                "计算层次[" + calculationNumberResults.getResultLayers() + "] " +
//                "计算结果[" + calculationNumberResults.getResult() + "]"
//                );
//
//                // 获取到比较运算计算组件
//                BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("b");
//                // 构建需要我们计算的公式
//                String s1 = "1 + (10 * 10) <= (10 * 10) + (1 * 1) + 1";
//                // 检查公式是否有错误
//                booleanCalculation2.check(s1);
//                // 开始计算公式
//                CalculationBooleanResults calculation = booleanCalculation2.calculation(s1);
//                // 打印计算结果
//                System.out.println(calculation.getLeft() + " <= " + calculation.getRight() + " " + calculation.getResult());
//
//                // 获取到累加运算计算组件
//                CumulativeCalculation c = CumulativeCalculation.getInstance("C");
//                // 构建需要我们计算的公式
//                String s2 = "n[0, 10, 1] (n + n)";
//                // 检查公式是否有错误
//                c.check(s2);
//                // 开始计算公式
//                CalculationNumberResults calculation1 = c.calculation(s2);
//                // 打印计算结果
//                System.out.println(calculation1.getResult());


// 实现一个名为n的函数 函数作用就是将数值 × 2
//        ManyToOneNumberFunction manyToOneNumberFunction = new ManyToOneNumberFunction("n") {
//            @Override
//            public double run(double... numbers) {
//                return numbers[0] * 2.0;
//            }
//        };
//        // 将函数注册
//        CalculationManagement.register(manyToOneNumberFunction);
//        // 获取到函数表达式解析组件
//        FunctionFormulaCalculation zhao11 = FunctionFormulaCalculation.getInstance("zhao12");
//        // 运算一个公式，在其中运算的表达式使用了函数 n
//        String s = "1 + (n(1 + 4) * n(2))";
//        zhao11.check(s);
//        CalculationNumberResults calculation1 = zhao11.calculation(s);
//        System.out.println(calculation1.getResult());