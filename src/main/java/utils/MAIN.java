package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation2;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 实现一个sum函数
        ManyToOneNumberFunction manyToOneNumberFunction = new ManyToOneNumberFunction("sum") {
            /**
             * 函数的运行逻辑实现
             *
             * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
             * @return 这里是数据经过函数转换之后的数据
             */
            @Override
            public double run(double... numbers) {
                double res = 0;
                for (double number : numbers) {
                    res += number;
                }
                return res;
            }
        };
        // 将该函数注册到管理者
        CalculationManagement.register(manyToOneNumberFunction);
        // 获取到新版本的函数计算组件
        FunctionFormulaCalculation2 functionFormulaCalculation2 = FunctionFormulaCalculation2.getInstance("zhao");
        // 构建我们需要计算的公式 TODO 在这个表达式中的函数sum形参，不只有1个，是多参的函数
        String s = "2 * (200 - sum(1 + 10.1, 2, 3)) + sum(10, 20)";
        // 启用共享池，能够加快计算的速度，计算的公式越复杂，该共享池的效果越显著
        functionFormulaCalculation2.setStartSharedPool(true);
        // 开始检查公式是否有错误
        functionFormulaCalculation2.check(s);
        // 获取到计算结果
        CalculationNumberResults calculation = functionFormulaCalculation2.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}