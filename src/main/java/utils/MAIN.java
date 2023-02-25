package utils;

import core.Mathematical_Expression;
import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.NumberCalculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println("这是一个示例代码样本！");
        // 构建一个需要被计算的函数表达式
        String s = "1 + 3 + (sum(1, 2, 3, 4, 9 - 1) - 8) + 1";
        // 实现 sum 函数 并注册到管理者中
        ManyToOneNumberFunction sumFunction = new ManyToOneNumberFunction("sum") {
            @Override
            public double run(double... numbers) {
                double res = 0;
                for (double number : numbers) {
                    res += number;
                }
                return res;
            }
        };
        // 将函数注册
        boolean isOk = Mathematical_Expression.register_function(sumFunction);
        if (isOk) {
            // 获取到多参数函数计算组件
            NumberCalculation fun = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2, "fun");
            // 检查数学表达式是否有错误
            fun.check(s);
            // 计算出结果并打印结果数据
            CalculationNumberResults results = fun.calculation(s);
            System.out.println(results);
            // 取消函数注册
            if (Mathematical_Expression.unregister_function("sum")) {
                System.out.println("oK!!!");
            }
            // 也可以使用函数对象取消函数注册
            if (Mathematical_Expression.unregister_function(sumFunction)) {
                System.out.println("oK!!!");
            }
            // 取消计算组件的实例化（可省略）
            if (CalculationManagement.unregister(fun.getName())) {
                System.out.println("unregister ok!!!");
            }
        }
    }
}