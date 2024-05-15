package io.github.beardedManZhao.mathematicalExpression.core.calculation;

import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

/**
 * 计算接口 其中提供不同计算组件针对数学公式的不同实现
 * <p>
 * The calculation interface provides different implementations of different calculation components for mathematical formulas
 *
 * @author zhao
 */
public interface Calculation {

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     * <p>
     * Format a formula so that it can be calculated by the calculation component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    String formatStr(String string);

    /**
     * @return 计算组件的名称
     */
    String getName();

    /**
     * 获取计算过程的信息
     * <p>
     * Obtain information about the calculation process
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 当前计算组件在计算一个表达式的时候，其计算过程的统计信息对象。
     * <p>
     * The statistical information object of the current calculation component's calculation process when calculating an expression.
     */
    default LogResults explain(String Formula, boolean formatRequired) {
        throw new UnsupportedOperationException("The current computing component cannot be used for " + this.getName() + ".explain(" + Formula + "), as it does not have the ability to perform process analysis~");
    }

    /**
     * 检查公式格式是否正确，如果不正确就会抛出一个异常
     * <p>
     * Check whether the formula format is correct. If not, an exception will be thrown
     *
     * @param string 需要被判断格式的数学运算公式
     *               <p>
     *               Mathematical operation formula of the format to be judged
     * @throws WrongFormat 在检查出来错误的时候进行该异常抛出，该异常中会记录格式检查时，查出来的格式错误。
     *                     <p>
     *                     The exception is thrown when an error is detected, and the format error found during the format check will be recorded in the exception.
     */
    void check(String string) throws WrongFormat;

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula 被计算的表达式，要求返回值是一个数值。
     *                <p>
     *                The returned value of the evaluated expression is required to be a numeric value.
     * @return 数值结果集对象，其中保存着每一步的操作数据，以及最终结果数值
     * <p>
     * Numerical result set object, which stores the operation data of each step and the final result value
     */
    CalculationResults calculation(String Formula);

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
    CalculationResults calculation(String Formula, boolean formatRequired);
}
