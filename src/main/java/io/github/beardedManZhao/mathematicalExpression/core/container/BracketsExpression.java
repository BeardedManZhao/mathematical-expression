package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.PrefixExpressionOperation;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;

/**
 * 数学表达式中 括号表达式会被编译成这个对象。
 *
 * @author zhao
 */
public class BracketsExpression extends PackExpression {


    /**
     * 新版括号表达式解析计算时，需要使用的第三方计算组件
     * <p>
     * Third party calculation components to be used when parsing and calculating the new bracket expression
     */
    protected final static PrefixExpressionOperation PREFIX_EXPRESSION_OPERATION = PrefixExpressionOperation.getInstance(CalculationManagement.PREFIX_EXPRESSION_OPERATION_NAME);


    protected BracketsExpression(NameExpression nameExpression, String packName) {
        super(nameExpression, packName);
    }

    /**
     * 将一个带有括号的表达式进行编译操作，值得注意的是，此操作为交叉编译，其在编译的时候，括号内部的编译操作会直接计算，编译结果不会保留。
     *
     * @param Formula              需要被编译的数学表达式
     * @param formatRequired       是否需要格式化
     * @param bracketsCalculation2 当编译操作进行时，如果遇到了括号，那么将会使用此参数进行计算。
     * @return 编译出来的数学表达式对象
     */
    public static PackExpression compile(String Formula, boolean formatRequired, BracketsCalculation2 bracketsCalculation2) {
        if (Mathematical_Expression.Options.isUseCache()) {
            // 计算出 key
            // 如果是使用缓存的就尝试获取一下缓存的表达式类
            final PackExpression cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(Formula);
            if (cacheCalculation != null) {
                return cacheCalculation;
            } else {
                // 代表缓存中没有
                final PackExpression compile = new BracketsExpression(
                        PREFIX_EXPRESSION_OPERATION.compile(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
                );
                // 编译好直接缓存
                Mathematical_Expression.Options.cacheCalculation(Formula, compile);
                // 然后返回结果
                return compile;
            }
        }
        return new BracketsExpression(
                PREFIX_EXPRESSION_OPERATION.compile(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
        );
    }

    /**
     * 将一个带有括号的表达式进行编译操作，值得注意的是，此操作为交叉编译，其在编译的时候，括号内部的编译操作会直接计算，编译结果不会保留。
     *
     * @param Formula              需要被编译的数学表达式
     * @param formatRequired       是否需要格式化
     * @param bracketsCalculation2 当编译操作进行时，如果遇到了括号，那么将会使用此参数进行计算。
     * @return 编译出来的数学表达式对象
     */
    public static PackExpression compileBigDecimal(String Formula, boolean formatRequired, BracketsCalculation2 bracketsCalculation2) {
        if (Mathematical_Expression.Options.isUseCache()) {
            // 计算出 key
            final String s = Formula + "uB";
            // 如果是使用缓存的就尝试获取一下缓存的表达式类
            final PackExpression cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(s);

            if (cacheCalculation != null) {
                return cacheCalculation;
            } else {
                // 代表缓存中没有
                final PackExpression compile = new BracketsExpression(
                        PREFIX_EXPRESSION_OPERATION.compileBigDecimal(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
                );
                // 编译好直接缓存
                Mathematical_Expression.Options.cacheCalculation(s, compile);
                // 然后返回结果
                return compile;
            }
        }
        return new BracketsExpression(
                PREFIX_EXPRESSION_OPERATION.compileBigDecimal(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
        );
    }

    /**
     * 解析出来表达式不带有括号的数学表达式，此操作会将括号内部的表达式直接进行计算！
     *
     * @param Formula              需要被编译的数学表达式
     * @param formatRequired       是否需要格式化
     * @param bracketsCalculation2 当编译操作进行时，如果遇到了括号，那么将会使用此参数进行计算。
     * @return 将括号内公式计算出来的数学表达式的结果！
     */
    private static StringBuilder parseStringBuilder(String Formula, boolean formatRequired, BracketsCalculation2 bracketsCalculation2) {
        int length = Formula.length();
        // 公式存储区
        final StringBuilder stringBuilder = new StringBuilder(length + 16);
        // 括号内数据的起始索引
        int start = 0;
        boolean setOk = false;
        // 括号内的括号均衡数量，为了确定是一对括号
        int count = 0;
        // 计算结果临时存储
        for (int i = 0; i < length; i++) {
            char aChar = Formula.charAt(i);
            if (aChar == ConstantRegion.LEFT_BRACKET) {
                // 如果当前字符是一个左括号，那么说明括号开始了，这个时候需要将起始点记录
                if (!setOk) {
                    setOk = true;
                    start = i + 1;
                }
                ++count;
            } else if (aChar == ConstantRegion.RIGHT_BRACKET && --count == 0) {
                setOk = false;
                // 如果当前字符是一个右括号，那么就将括号中的字符进行递归计算，计算之后将该参数作为公式的一部分
                CalculationNumberResults calculation = bracketsCalculation2.calculation(Formula.substring(start, i), formatRequired);
                stringBuilder.append(calculation.getResult());
            } else if (!setOk && aChar != ConstantRegion.EMPTY) {
                // 如果不是一个括号就将字符提供给字符串缓冲区
                stringBuilder.append(aChar);
            }
        }
        return stringBuilder;
    }

}
