package io.github.beardedManZhao.mathematicalExpression.core.calculation.bool;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.NumberCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationBooleanResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

/**
 * 计算一个比较表达式的计算组件的父类，计算结果一般都是包含布尔值结果的对象
 * <p>
 * Compute the parent class of the calculation component of a comparison expression. The calculation result is generally an object containing Boolean results
 *
 * @author zhao
 */
public abstract class BooleanCalculation implements Calculation {

    protected final String Name;
    /**
     * 在进行该模块的计算时，需要使用到的第三方计算组件
     */
    protected NumberCalculation numberCalculation = BracketsCalculation2.getInstance(CalculationManagement.BRACKETS_CALCULATION_2_NAME);

    protected BooleanCalculation(String name) {
        Name = name;
    }

    /**
     * 为该组件配置数值计算过程中所采用的模块，特别是针对“比较表达式”的运算，需依托于高效的数值计算模块。我们赋予您这一自定义能力，旨在让您能灵活选择或设计数值计算模块，从而大幅提高系统应对多样需求的灵活性。
     * <p>
     * Configuring the module utilized by this component during numerical computations, particularly for "expression comparison," necessitates the integration of a robust numerical calculation module. Our provision of this customization capability aims to enable you to handpick or design your numerical computation modules, thereby significantly enhancing the adaptability and flexibility of the system to diverse requirements.
     *
     * @param numberCalculation 数值计算组件，如果您没有调用过此函数，则默认是使用的 BracketsCalculation2
     *                          <p>
     *                          Numerical Calculation Component - if you have not invoked this function, the default module in use is BracketsCalculation2.
     */
    public void setCalculation(NumberCalculation numberCalculation) {
        this.numberCalculation = numberCalculation;
    }

    /**
     * 为该组件配置数值计算过程中所采用的模块，特别是针对“比较表达式”的运算，需依托于高效的数值计算模块。我们赋予您这一自定义能力，旨在让您能灵活选择或设计数值计算模块，从而大幅提高系统应对多样需求的灵活性。
     * <p>
     * Configuring the module utilized by this component during numerical computations, particularly for "expression comparison," necessitates the integration of a robust numerical calculation module. Our provision of this customization capability aims to enable you to handpick or design your numerical computation modules, thereby significantly enhancing the adaptability and flexibility of the system to diverse requirements.
     *
     * @param numberCalculation 数值计算组件，如果您没有调用过此函数，则默认是使用的 BracketsCalculation2
     *                          <p>
     *                          Numerical Calculation Component - if you have not invoked this function, the default module in use is BracketsCalculation2.
     */
    public void setCalculation(Mathematical_Expression numberCalculation) {
        this.setCalculation((NumberCalculation) Mathematical_Expression.getInstance(numberCalculation));
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     * <p>
     * Format a formula so that it can be calculated by the calculation component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string.replaceAll("\\s+", ConstantRegion.NO_CHAR);
    }

    /**
     * @return 计算组件的名称
     */
    @Override
    public String getName() {
        return this.Name;
    }

    /**
     * 检查公式格式是否正确，如果不正确就会抛出一个异常
     * <p>
     * Check whether the formula format is correct. If not, an exception will be thrown
     *
     * @param string 需要被判断格式的数学运算公式
     *               <p>
     *               Mathematical operation formula of the format to be judged
     */
    @Override
    public void check(String string) throws WrongFormat {
        // 先按照表达式的比较运算符进行一个切分
        final String[] split = ConstantRegion.REGULAR_COMPARISON_OPERATOR_PATTERN.split(string);
        // 判断是否属于布尔表达式，使用切分之后是否有两个表达式判断
        if (split.length == 2) {
            // 检查表达式两边是否符合条件
            final String left = split[0];
            final String right = split[1];
            if (!ConstantRegion.STRING_NULL.equals(left)) numberCalculation.check(left);
            if (!ConstantRegion.STRING_NULL.equals(right)) numberCalculation.check(right);
        } else {
            // 如果比较运算符两边的表达式不是2个，说明不是一个布尔表达式
            throw new WrongFormat("发生了错误，您的布尔表达式中，存在着数量不正确的比较运算符\n" +
                    "An error has occurred. There is an incorrect number of comparison operators in your Boolean expression\n" +
                    "Number of comparison operators [" + (split.length - 1) + "]");
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
    public abstract CalculationBooleanResults calculation(String Formula, boolean formatRequired);

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula 被计算的表达式，要求返回值是一个数值。
     *                <p>
     *                The returned value of the evaluated expression is required to be a numeric value.
     * @return 布尔结果对象，其中存储着有关结果以及其计算过程的一些信息
     * <p>
     * Boolean result object, which stores some information about the result and its calculation process
     * @see CalculationBooleanResults
     */
    public CalculationBooleanResults calculation(String Formula) {
        return calculation(Formula, true);
    }

    @Override
    public boolean isBigDecimal() {
        return this.numberCalculation.isBigDecimal();
    }

    @Override
    public void setBigDecimal(boolean bigDecimal) {
        this.numberCalculation.setBigDecimal(bigDecimal);
    }
}
