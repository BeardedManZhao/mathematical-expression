package io.github.beardedManZhao.mathematicalExpression.core.calculation.bool;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationBooleanResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;
import io.github.beardedManZhao.mathematicalExpression.utils.NumberUtils;

/**
 * 计算一个布尔返回值的表达式，该组件针对两个表达式或数值之间的比较来计算结果数值，用于比较表达式是否成立
 * <p>
 * An expression that calculates a Boolean return value. This component calculates the result value for the comparison between two expressions or values, and is used to compare whether the expression is valid
 *
 * @author zhao
 */
public class BooleanCalculation2 extends BooleanCalculation {

    protected BooleanCalculation2(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析比较运算公式的计算组件对象
     */
    public static BooleanCalculation2 getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof BooleanCalculation2) {
                return (BooleanCalculation2) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 BooleanCalculation2 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            BooleanCalculation2 BooleanCalculation2 = new BooleanCalculation2(CalculationName);
            CalculationManagement.register(BooleanCalculation2, false);
            return BooleanCalculation2;
        }
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 数值结果集对象，其中保存着每一步的操作数据，以及最终结果数值
     * <p>
     * Numerical result set object, which stores the operation data of each step and the final result value
     */
    @Override
    public CalculationBooleanResults calculation(String Formula, boolean formatRequired) {
        final String NewFormula;
        if (formatRequired) {
            NewFormula = super.formatStr(Formula);
        } else {
            NewFormula = Formula;
        }
        // 先按照表达式的比较运算符进行一个切分
        final String[] split = ConstantRegion.REGULAR_COMPARISON_OPERATOR_PATTERN.split(NewFormula);
        final String s1 = split[0];
        final String s2 = split[1];
        // 进行比较运算符的提取
        final int start = s1.length();
        int end = start + 1;
        while (!NewFormula.substring(end).equals(s2)) {
            ++end;
        }
        String s = NewFormula.substring(start, end);
        {
            final String Null = ConstantRegion.STRING_NULL;
            // 判断左右是否有一个为null
            if (s1.equalsIgnoreCase(Null)) {
                // 如果左边为null ，同时右边为null就代表两个值相同，在这里直接将两个值赋值0
                int aNull = s2.equalsIgnoreCase(Null) ? 0 : 1;
                return new CalculationBooleanResults(
                        NumberUtils.ComparisonOperation(s, 0, aNull),
                        this.Name, 1, 0, aNull
                );
            } else if (s2.equalsIgnoreCase(Null)) {
                // 如果左边不是null 但是 右边为null，就直接将 1 比较 0 的值算出来
                return new CalculationBooleanResults(
                        NumberUtils.ComparisonOperation(s, 1, 0),
                        this.Name, 1, 1, 0
                );
            }
        }
        // 进行左值的结果计算
        final CalculationNumberResults calculation1 = BRACKETS_CALCULATION_2.calculation(s1);
        // 进行右值的结果计算
        final CalculationNumberResults calculation2 = BRACKETS_CALCULATION_2.calculation(s2);
        // 进行表达式结果的计算
        double left = calculation1.getResult();
        double right = calculation2.getResult();
        return new CalculationBooleanResults(NumberUtils.ComparisonOperation(s, left, right), this.Name, calculation1.getResultLayers() + calculation2.getResultLayers(), left, right);
    }
}
