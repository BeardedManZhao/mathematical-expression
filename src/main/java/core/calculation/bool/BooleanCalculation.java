package core.calculation.bool;

import core.calculation.Calculation;
import core.calculation.number.BracketsCalculation2;
import core.container.CalculationBooleanResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计算一个比较表达式的计算组件的父类，计算结果一般都是包含布尔值结果的对象
 * <p>
 * Compute the parent class of the calculation component of a comparison expression. The calculation result is generally an object containing Boolean results
 *
 * @author zhao
 */
public abstract class BooleanCalculation implements Calculation {

    /**
     * 在进行该模块的计算时，需要使用到的第三方计算组件
     */
    protected final static BracketsCalculation2 BRACKETS_CALCULATION_2 = BracketsCalculation2.getInstance(CalculationManagement.BRACKETS_CALCULATION_2_NAME);
    protected final String Name;
    protected final Logger LOGGER;

    protected BooleanCalculation(String name) {
        Name = name;
        LOGGER = LoggerFactory.getLogger(name);
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
        String[] split = string.split(ConstantRegion.REGULAR_COMPARISON_OPERATOR);
        // 判断是否属于布尔表达式，使用切分之后是否有两个表达式判断
        if (split.length == 2) {
            // 检查表达式两边是否符合条件
            String left = split[0];
            String right = split[1];
            if (!ConstantRegion.STRING_NULL.equals(left)) BRACKETS_CALCULATION_2.check(left);
            if (!ConstantRegion.STRING_NULL.equals(right)) BRACKETS_CALCULATION_2.check(right);
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
     * @see core.container.CalculationBooleanResults
     */
    public CalculationBooleanResults calculation(String Formula) {
        return calculation(Formula, true);
    }
}
