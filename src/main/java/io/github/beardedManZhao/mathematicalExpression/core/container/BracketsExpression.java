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


    protected BracketsExpression(String expressionName, NameExpression nameExpression, String packName) {
        super(expressionName, nameExpression, packName);
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
                final PackExpression compile = new BracketsExpression(Formula,
                        PREFIX_EXPRESSION_OPERATION.compile(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
                );
                // 编译好直接缓存
                Mathematical_Expression.Options.cacheCalculation(Formula, compile);
                // 然后返回结果
                return compile;
            }
        }
        return new BracketsExpression(Formula,
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
                final PackExpression compile = new BracketsExpression(Formula,
                        PREFIX_EXPRESSION_OPERATION.compileBigDecimal(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
                );
                // 编译好直接缓存
                Mathematical_Expression.Options.cacheCalculation(s, compile);
                // 然后返回结果
                return compile;
            }
        }
        return new BracketsExpression(Formula,
                PREFIX_EXPRESSION_OPERATION.compileBigDecimal(parseStringBuilder(Formula, formatRequired, bracketsCalculation2).toString(), formatRequired), bracketsCalculation2.getName()
        );
    }

    /**
     * 解析出来表达式不带有括号的数学表达式，此操作会将括号内部的表达式直接进行计算！
     *
     * @param formula              需要被编译的数学表达式
     * @param formatRequired       是否需要格式化
     * @param bracketsCalculation2 当编译操作进行时，如果遇到了括号，那么将会使用此参数进行计算。
     * @return 将括号内公式计算出来的数学表达式的结果！
     */
    private static StringBuilder parseStringBuilder(
            String formula,
            boolean formatRequired,
            BracketsCalculation2 bracketsCalculation2
    ) {

        final int length = formula.length();

        final StringBuilder result = new StringBuilder(length);

        int level = 0;
        int start = -1;

        for (int i = 0; i < length; i++) {

            char c = formula.charAt(i);

            if (c == ConstantRegion.LEFT_BRACKET) {

                if (level == 0) {
                    start = i + 1;
                }

                level++;
                continue;
            }

            if (c == ConstantRegion.RIGHT_BRACKET) {

                level--;

                if (level == 0) {

                    CalculationNumberResults calculation =
                            bracketsCalculation2.calculation(
                                    formula.substring(start, i),
                                    formatRequired
                            );

                    result.append(calculation.getResult());
                }

                continue;
            }

            if (level == 0 && c != ConstantRegion.EMPTY) {
                result.append(c);
            }
        }

        return result;
    }

}
