package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.ExtractException;
import exceptional.WrongFormat;
import utils.NumberUtils;
import utils.StrUtils;

import java.util.Stack;

/**
 * 解析一个无括号的数学计算公式的组件，针对不含括号的计算公式，该组件可以提供计算支持
 * <p>
 * Parse a component of mathematical calculation formula without brackets. This component can provide calculation support for calculation formula without brackets
 *
 * @author zhao
 */
public class PrefixExpressionOperation extends NumberCalculation {

    protected PrefixExpressionOperation(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static PrefixExpressionOperation getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof PrefixExpressionOperation) {
                return (PrefixExpressionOperation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 PrefixExpressionOperation 类型，请您重新定义一个名称。\n" +
                                "The calculation component [" + CalculationName + "] you need has been found, but it does not seem to belong to the PrefixExpressionOperation type. Please redefine a name."
                );
            }
        } else {
            PrefixExpressionOperation PrefixExpressionOperation = new PrefixExpressionOperation(CalculationName);
            CalculationManagement.register(PrefixExpressionOperation, false);
            return PrefixExpressionOperation;
        }
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
        if (string.matches(ConstantRegion.REGULAR_CONTAINS_BRACKET)) {
            throw new WrongFormat("本组件只能解析不包含括号的表达式！！！\nThis component can only parse expressions without parentheses!!!\nWrong format => " + string);
        }
        super.check(string);
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string.replaceAll(" +", "") + ConstantRegion.PLUS_SIGN + '0';
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
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        final char[] newFormula;
        if (formatRequired) {
            newFormula = formatStr(Formula).toCharArray();
        } else {
            newFormula = Formula.replaceAll(" +", "").toCharArray();
        }
        // 创建操作符栈
        final Stack<Double> doubleStack = new Stack<>();
        // 创建操作数栈
        final Stack<Character> characterStack = new Stack<>();
        // 开始格式化，将符号与操作数进行分类
        final StringBuilder stringBuilder = new StringBuilder(newFormula.length);
        for (char c : newFormula) {
            if (StrUtils.IsAnOperator(c)) {
                // 如果是操作符，就先将上一个数值计算出来
                double number = StrUtils.stringToDouble(stringBuilder.toString());
                if (characterStack.isEmpty()) {
                    // 如果栈为空 直接将运算符添加到栈顶
                    characterStack.push(c);
                    // 将数值添加到数值栈顶
                    doubleStack.push(number);
                } else {
                    // 如果不为空就检查栈顶的与当前运算符的优先级
                    if (!NumberUtils.PriorityComparison(characterStack.peek(), c)) {
                        // 如果上一个优先级较大 就将上一个操作符取出
                        char top = characterStack.pop();
                        // 将上一个优先级高的值 与当下优先级较低的值进行运算，然后将当下的新数值和当下的操作符推到栈顶
                        doubleStack.push(NumberUtils.calculation(top, doubleStack.pop(), number));
                        characterStack.push(c);
                    } else {
                        // 反之就将当前运算符提供到栈顶
                        characterStack.push(c);
                        doubleStack.push(number);
                    }
                }
                // 清理所有的字符缓冲
                stringBuilder.delete(0, stringBuilder.length());
            } else if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == ConstantRegion.DECIMAL_POINT) {
                // 如果是数值的某一位，就将数值存储到变量中
                stringBuilder.append(c);
            }
        }
        doubleStack.push(StrUtils.stringToDouble(stringBuilder.toString()));
        double res = doubleStack.get(0);
        char back;
        final int size = doubleStack.size();
        Double[] temps = new Double[size - 1];
        // 开始计算
        final int sizeD2 = size << 1;
        for (int i = 1, offset = 0; i < size && offset < sizeD2; ++offset, ++i) {
            // 更新操作符
            back = characterStack.get(offset);
            // 获取操作数并计算结果
            res = NumberUtils.calculation(back, res, doubleStack.get(i));
            temps[offset] = res;
        }
        // 返回结果
        return new CalculationNumberResults(temps, res, this.Name);
    }
}
