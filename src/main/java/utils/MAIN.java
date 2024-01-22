package utils;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到括号表达式计算组件，这里就是为获取到的组件起一个唯一的名字
        // 这个操作会将当前组件实例化，以name的名字添加到管理者中，如果这个名字在之前就已经使用过，只需要更换一个就好啦
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 构建一个数学表达式 这是一个嵌套数学表达式，计算复杂表达式针对这个框架来说是没有问题的哦！
        String s = "1.2+1.3*2+(2-1)";
        // 检查数学表达式是否有错误，针对确定没有问题的数学表达式，也可以不检查，如果检查出错误，这里会抛出错误信息
        instance.check(s);
        // 计算数学表达式，会返回一个结果对象，其中存储着运算结果以及运算过程中的信息
        CalculationNumberResults calculation = (CalculationNumberResults) instance.calculation(s);
        System.out.println(calculation.getResult());

    }
}