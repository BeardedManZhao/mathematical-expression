package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StrUtils;

/**
 * 计算结果为数值的数学表达式结果，其中提供了数学表达式的计算函数
 * <p>
 * The calculation result is the numerical mathematical expression result, in which the calculation function of the mathematical expression is provided
 *
 * @author zhao
 */
public abstract class NumberCalculation implements Calculation {
    protected final String Name;
    protected final Logger LOGGER;

    protected NumberCalculation(String name) {
        Name = name;
        LOGGER = LoggerFactory.getLogger(name);
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
    public abstract CalculationNumberResults calculation(String Formula, boolean formatRequired);

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula 被计算的表达式，要求返回值是一个数值。
     *                <p>
     *                The returned value of the evaluated expression is required to be a numeric value.
     * @return 数值结果对象，该返回值是一个专用于存储数值计算结果的包装类，其中有着计算数据的记录以及结果的保存，可以直接获取到
     * <p>
     * Numerical result object. The returned value is a wrapper class dedicated to storing numerical calculation results. It contains the records of calculation data and the saving of results, which can be directly obtained
     * @see core.container.CalculationNumberResults
     */
    public CalculationNumberResults calculation(String Formula) {
        return calculation(Formula, true);
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
        if (string.isEmpty()) {
            throw new WrongFormat("您传入的表达式为null 无法进行计算。");
        }
        int lastIndex = string.length() - 1;
        // 左括号出现数量
        int LeftCount = 0;
        // 右括号出现数量
        int RightCount;
        {
            char lastChar = string.charAt(lastIndex);
            while (lastChar == ConstantRegion.EMPTY) {
                lastChar = string.charAt(--lastIndex);
            }
            if (!StrUtils.IsANumber(lastChar)) {
                if (lastChar != ConstantRegion.RIGHT_BRACKET) {
                    throw new WrongFormat("您传入的表达式格式有误，最后一个字符不是一个数值！！！\nThe format of the expression you passed in is incorrect. The last character is not a numeric value!!!\nERROR => " + lastChar);
                } else {
                    RightCount = 1;
                }
            } else {
                RightCount = 0;
            }
        }
        // 上一个字符是否是运算符
        boolean is = false;
        for (int i = 0; i < lastIndex; i++) {
            char c = string.charAt(i);
            if (c == ConstantRegion.LEFT_BRACKET) {
                ++LeftCount;
            } else if (c == ConstantRegion.RIGHT_BRACKET) {
                ++RightCount;
            } else if (!(StrUtils.IsANumber(c) || c == ConstantRegion.DECIMAL_POINT || c == ConstantRegion.EMPTY)) {
                if (!StrUtils.IsAnOperator(c)) {
                    throw new WrongFormat("解析表达式的时候出现了未知符号!!!\nUnknown symbol appears when parsing expression!!!\nWrong format => [" + c + "] from " + string);
                } else {
                    if (is) throw new WrongFormat("您的数学表达式不正确，缺失了一个运算数值或多出了一个运算符。ERROR => " + string);
                    is = true;
                    continue;
                }
            }
            is = false;
        }
        if (LeftCount != RightCount) {
            int abs = Math.abs(LeftCount - RightCount);
            throw new WrongFormat("您的格式不正确，出现了数学表达式中不正确的括号对数，请您检查是否缺少或者多出了[" + abs + "]个括号。\n" +
                    "Your format is incorrect. There are incorrect parenthesis logarithms in the mathematical expression. Please check whether [" + abs + "] parentheses are missing or extra.\n" +
                    "Wrong from [" + string + "]");
        }
    }

    /**
     * @return 计算组件的名称
     */
    @Override
    public String getName() {
        return Name;
    }
}
