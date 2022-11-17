package core.calculation.number;

import exceptional.AbnormalOperation;
import utils.NumberUtils;
import utils.StrUtils;

/**
 * 括号解析算法计算一个公式的计算组件的父类，其中的计算具体实现是一个抽象，等待实现
 * <p>
 * The bracket parsing algorithm calculates the parent class of the calculation component of a formula, in which the specific implementation of the calculation is an abstract, waiting to be implemented
 *
 * @author zhao
 */
public abstract class BracketsCalculation extends NumberCalculation {
    protected BracketsCalculation(String name) {
        super(name);
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算，这里是将字符串格式化成为能够被括号解析组件计算的公式
     * <p>
     * Format a formula so that it can be calculated by the calculation component. Here is to format the string into a formula that can be calculated by the bracket resolution component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string.replaceAll("\\s+", "");
    }

    /**
     * 计算一个带有两个操作数 一个操作符的计算公式的结果
     *
     * @param BinaryFormula 公式 例如 1 + 2
     * @return 计算结果数值
     */
    public double calculation2(String BinaryFormula) {
        final StringBuilder a = new StringBuilder();
        final StringBuilder b = new StringBuilder();
        boolean isOk = false;
        char Operator = 0;
        for (char c : BinaryFormula.toCharArray()) {
            if (!isOk) {
                if (StrUtils.IsAnOperator(c)) {
                    Operator = c;
                    isOk = true;
                } else {
                    b.append(c);
                }
            } else {
                a.append(c);
            }
        }
        if (Operator != 0) {
            return NumberUtils.calculation(Operator, StrUtils.stringToDouble(a.toString()), StrUtils.stringToDouble(b.toString()));
        } else {
            throw new AbnormalOperation("您的公式格式错误，未解析成功，请您检查您的格式哦！\n" +
                    "The format of your formula is wrong, and it was not parsed successfully. Please check your format!\n" +
                    "error format => " + BinaryFormula);
        }
    }
}
