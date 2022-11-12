package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 计算结果为数值的数学表达式结果，其中提供了数学表达式的计算函数
 *
 * The calculation result is the numerical mathematical expression result, in which the calculation function of the mathematical expression is provided
 */
public abstract class NumberCalculation implements Calculation {

    /**
     * 合法字符，一个数学表达式的格式中可以包含的所有字符，包含这些字符外的字符，在格式化的时候，将直接判定为格式不正确
     */
    public static final HashSet<Character> LEGAL_CHARACTERS = new HashSet<>(
            Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '+', '-', '*', '/', '%', '(', ')', '.')
    );
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
        // 左括号出现数量
        int LeftCount = 0;
        // 右括号出现数量
        int RightCount = 0;
        for (char c : string.toCharArray()) {
            if (c == '(') {
                ++LeftCount;
            } else if (c == ')') {
                ++RightCount;
            } else if (!NumberCalculation.LEGAL_CHARACTERS.contains(c)) {
                throw new WrongFormat("您的格式不正确，出现了数学表达式中不应该存在的字符。\n" +
                        "Your format is incorrect. There are characters that should not exist in the mathematical expression.\n" +
                        "Wrong character [" + c + "] from [" + string + "]");
            }
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
