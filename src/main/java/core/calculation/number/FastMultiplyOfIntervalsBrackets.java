package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.ExtractException;
import utils.NumberUtils;

/**
 * 快速的将一个区间内所有元素的累积结果计算出来，该组件继承于“FastSumOfIntervalsBrackets” 父类中的“step” 字段在这里还是作为区间步长，默认为2
 * <p>
 * Quickly calculate the cumulative results of all elements in an interval. This component inherits the "step" field in the parent class of "FastSumOfIntervalsBrackets". Here, it is still used as the interval step. The default value is 2
 *
 * @author zhao
 */
public class FastMultiplyOfIntervalsBrackets extends FastSumOfIntervalsBrackets {
    protected FastMultiplyOfIntervalsBrackets(String name) {
        super(name);
    }


    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static FastMultiplyOfIntervalsBrackets getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof FastMultiplyOfIntervalsBrackets) {
                return (FastMultiplyOfIntervalsBrackets) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 FastMultiplyOfIntervalsBrackets 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            FastMultiplyOfIntervalsBrackets FastMultiplyOfIntervalsBrackets = new FastMultiplyOfIntervalsBrackets(CalculationName);
            CalculationManagement.register(FastMultiplyOfIntervalsBrackets, false);
            FastMultiplyOfIntervalsBrackets.step = 2;
            return FastMultiplyOfIntervalsBrackets;
        }
    }

    /**
     * 将两个结果对象，作为需要求和区间的起始与终止数值，以此进行区间的求和，不进行公式的计算
     * <p>
     * Take the two result objects as the starting and ending values of the interval to be summed, so as to sum the interval, without calculating the formula
     *
     * @param start 起始点结果数值
     * @param end   终止点结果数值
     * @return 区间求和的结果对象
     * <p>
     * Result object of interval summation
     */
    @Override
    public CalculationNumberResults calculation(CalculationNumberResults start, CalculationNumberResults end) {
        return new CalculationNumberResults(
                end.getResultLayers() + start.getResultLayers(),
                NumberUtils.MultiplyOfRange(start.getResult(), end.getResult(), step),
                this.Name
        );
    }
}
