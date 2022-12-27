package utils;

import core.calculation.number.FastMultiplyOfIntervalsBrackets;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

/**
 * 测试用类
 */
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到区间求和快计算组件
        FastMultiplyOfIntervalsBrackets fast = FastMultiplyOfIntervalsBrackets.getInstance("fast");
        // 构建一个需要计算的表达式 下面的表达式代表 从 11 = (1+10) 乘到 13 = (20-(5+2)) 默认等差为2
        // 结果应为 11 * 13 = 143
        String s = "1 + 10, 20 +- (5 + 2)";
        // 检查表达式，共享池从1.2版本后，已经是默认启用的状态了！不需要手动设置了
        // fast.setStartSharedPool(true);
        fast.check(s);
        // 从1.2版本之后，累加组件支持设置步长参数，1.2.1版本中开始正式支持步长区间的求和
        fast.step = 2;
        // 开始计算
        CalculationNumberResults calculation = fast.calculation(s);
        // 打印计算结果
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}