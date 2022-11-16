package core.calculation;

import exceptional.WrongFormat;

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
}
