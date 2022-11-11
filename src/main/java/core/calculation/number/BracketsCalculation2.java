package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.ExtractException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 将一个带有嵌套括号的表达式数值结果计算，能够识别嵌套括号的就按优先级，是括号表达式计算的实现类
 * <p>
 * Compute the numerical results of an expression with nested parentheses. Those that can recognize nested parentheses are prioritized. They are the implementation classes of the calculation of parenthesis expressions
 *
 * @author zhao
 */
public class BracketsCalculation2 extends BracketsCalculation {

    /**
     * 新版括号表达式解析计算时，需要使用的第三方计算组件
     * <p>
     * Third party calculation components to be used when parsing and calculating the new bracket expression
     */
    protected final static PrefixExpressionOperation PREFIX_EXPRESSION_OPERATION = PrefixExpressionOperation.getInstance(CalculationManagement.PREFIX_EXPRESSION_OPERATION_NAME);

    protected BracketsCalculation2(String name) {
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
    public static BracketsCalculation2 getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof BracketsCalculation2) {
                return (BracketsCalculation2) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 BracketsCalculation2 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            BracketsCalculation2 BracketsCalculation2 = new BracketsCalculation2(CalculationName);
            CalculationManagement.register(BracketsCalculation2, false);
            return BracketsCalculation2;
        }
    }

    /**
     * 解析带有括号的公式，并将每一个括号的计算结果进行汇总，然后将结果对象返回
     * <p>
     * Parse the formula with brackets, summarize the calculation results of each bracket, and then return the result object
     *
     * @param Formula        带有嵌套括号的公式
     *                       <p>
     *                       Formulas with nested parentheses
     * @param formatRequired 是否需要格式化，在此处进行格式化的设置
     *                       <p>
     *                       Whether format is required, set the format here
     * @return 返回一个结果对象
     * <p>
     * Returns a result object
     */
    @Override
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        // 公式存储区
        final StringBuilder stringBuilder = new StringBuilder();
        // 迭代每一个字符
        char[] chars = Formula.toCharArray();
        // 括号内数据的起始索引
        int start = 0;
        boolean setok = false;
        // 括号内的括号均衡数量，为了确定是一对括号
        int count = 0;
        // 计算结果临时存储
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == '(') {
                // 如果当前字符是一个左括号，那么说明括号开始了，这个时候需要将起始点记录
                if (!setok) {
                    start = i + 1;
                }
                ++count;
                setok = true;
            } else if (aChar == ')' && --count == 0) {
                setok = false;
                // 如果当前字符是一个右括号，那么就将括号中的字符进行递归计算，计算之后将该参数作为公式的一部分
                CalculationNumberResults calculation = calculation(Formula.substring(start, i), true);
                stringBuilder.append(calculation.getResult());
                arrayList.addAll(Arrays.asList(calculation.getDoubles()));
            } else if (!setok && aChar != ' ') {
                // 如果不是一个括号就将字符提供给字符串缓冲区
                stringBuilder.append(aChar);
            }
        }
        // 将此字符串的结果计算出来
        double result = PREFIX_EXPRESSION_OPERATION.calculation(stringBuilder.toString(), true).getResult();
        arrayList.add(result);
        return new CalculationNumberResults(arrayList.toArray(new Double[0]), result, this.Name);
    }
}