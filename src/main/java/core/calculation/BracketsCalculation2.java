package core.calculation;

import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.ExtractException;

public class BracketsCalculation2 extends BracketsCalculation {

    /**
     * 新版括号表达式解析计算时，需要使用的第三方计算组件
     */
    PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance(CalculationManagement.PREFIX_EXPRESSION_OPERATION_NAME);

    private BracketsCalculation2(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static BracketsCalculation2 getInstance(String CalculationName) {
        if (CalculationManagement.isregister(CalculationName)) {
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
            CalculationManagement.register(BracketsCalculation2);
            return BracketsCalculation2;
        }
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算，这里是将字符串格式化成为能够被括号解析组件计算的公式
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return super.formatStr(string);
    }

    /**
     * 解析带有括号的公式，并将每一个括号的计算结果进行汇总，然后将结果对象返回
     *
     * @param Formula        带括号的公式
     * @param formatRequired 是否需要格式化，在此处进行格式化的设置
     * @return 返回一个结果对象
     */
    @Override
    public CalculationNumberResults calculationBrackets(String Formula, boolean formatRequired) {
        // 公式存储区
        final StringBuilder stringBuilder = new StringBuilder();
        // 迭代每一个字符
        char[] chars = Formula.toCharArray();
        // 括号内数据的起始索引
        int start = 0;
        boolean setok = false;
        // 括号内的括号均衡数量，为了确定是一对括号
        int count = 0;
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
                stringBuilder.append(calculationBrackets(Formula.substring(start, i), true).getResult());
            } else if (!setok && aChar != ' ') {
                // 如果不是一个括号就将字符提供给字符串缓冲区
                stringBuilder.append(aChar);
            }
        }
        // 将此字符串的结果计算出来
        return prefixExpressionOperation.calculation(stringBuilder.toString(), true);
    }

    /**
     * 计算一个带有多个操作数 一个操作符的计算公式的结果
     *
     * @param Formula 不带括号的公式 例如 1 + 2
     * @return 计算结果数值
     */
    @Override
    public double calculation(String Formula) {
        return prefixExpressionOperation.calculation(Formula, true).getResult();
    }
}
