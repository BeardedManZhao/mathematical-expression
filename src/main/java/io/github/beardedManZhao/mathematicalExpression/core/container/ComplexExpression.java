package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;

import java.util.PrimitiveIterator;

/**
 * 复数表达式对象 其可以将一个复数字符串编译为一个复数对象。
 * <p>
 * A complex expression object can compile a complex string into a complex object.
 *
 * @author zhao
 */
public class ComplexExpression extends NameExpression {
    /**
     * 左右两边的函数表达式对象 分别是 实部 和 虚部
     */
    private final FunctionExpression functionExpression1, functionExpression2;
    /**
     * 操作运算符，两个复数对象之间的运算符！
     */
    private final char sign;

    public ComplexExpression(String expression, String calculationName, FunctionExpression functionExpression1, FunctionExpression functionExpression2, char sign) {
        super(expression, calculationName);
        this.functionExpression1 = functionExpression1;
        this.functionExpression2 = functionExpression2;
        this.sign = sign;
    }

    /**
     * 将一个复数表达式编译为表达式的对象。
     *
     * @param expression      需要被编译的表达式，表达式中可以支持 (a+b-f(c)) + 2 - 1 i 这样的表达式，会将第一个无括号包裹的操作符做为实部 与 虚部 的运算符！
     * @param calculationName 表达式的名字，通常情况下，在计算组件中会使用到！
     * @return 表达式的对象
     */
    public static ComplexExpression compile(String expression, String calculationName) {
        // 准备容器 存储左右两边的复数表达式
        FunctionExpression functionExpression1, functionExpression2;
        // 首先准备计数器，当前面的右括号与左括号一样的时候 此数值应为0
        int count = 0;
        char sign;
        final StringBuilder stringBuilder = new StringBuilder(expression.length());
        final PrimitiveIterator.OfInt iterator = expression.chars().iterator();
        while (iterator.hasNext()) {
            final char c1 = (char) iterator.next().intValue();
            switch (c1) {
                case ConstantRegion.LEFT_BRACKET:
                    ++count;
                    break;
                case ConstantRegion.RIGHT_BRACKET:
                    --count;
                    break;
                case ConstantRegion.PLUS_SIGN:
                case ConstantRegion.MINUS_SIGN:
                    if (count == 0) {
                        // 代表当前的括号结束了 且是运算符 因此直接处理 在这里编译出表达式 这里是实部的
                        functionExpression1 = FunctionExpression.compile(stringBuilder.toString(), calculationName + ".left");
                        // 然后编译出剩下的部分 但是要在这里找一下 i 的位置
                        final int i = expression.lastIndexOf('i');
                        // 将虚部解析出来
                        functionExpression2 = FunctionExpression.compile(expression.substring(stringBuilder.length(), i), calculationName + ".right");
                        // 然后获取到运算符
                        sign = c1;
                        // 结束循环
                        return new ComplexExpression(expression, calculationName, functionExpression1, functionExpression2, sign);
                    }
            }
            stringBuilder.append(c1);
        }
        throw new RuntimeException(expression + " is not a complex expression.");
    }

    @Override
    public boolean isBigDecimal() {
        return this.functionExpression1.isBigDecimal();
    }

    @Override
    public boolean isUnBigDecimal() {
        return this.functionExpression1.isUnBigDecimal();
    }

    @Override
    public void convertToMultiPrecisionSupported() {
        this.functionExpression1.convertToMultiPrecisionSupported();
        this.functionExpression2.convertToMultiPrecisionSupported();
    }

    @Override
    public CalculationComplexResults calculation(boolean isCopy) {
        // 左边计算出来
        final CalculationResults calculation0 = this.functionExpression1.calculationCache(isCopy);
        // 右边计算出来
        final CalculationResults calculation1 = this.functionExpression2.calculationCache(isCopy);
        // 使用计算结果
        return new CalculationComplexResults(
                calculation0.getResultLayers() + calculation1.getResultLayers(),
                (double) calculation0.getResult(), this.sign == ConstantRegion.MINUS_SIGN ? -(double) calculation1.getResult() : (double) calculation1.getResult(),
                this.getCalculationName()
        );

    }

    @Override
    public CalculationComplexResults calculationBigDecimals(boolean isCopy) {
        // 左边计算出来
        final CalculationResults calculation0 = this.functionExpression1.calculationBigDecimalsCache(isCopy);
        // 右边计算出来
        final CalculationResults calculation1 = this.functionExpression2.calculationBigDecimalsCache(isCopy);
        // 使用计算结果
        return new CalculationComplexResults(
                calculation0.getResultLayers() + calculation1.getResultLayers(),
                (double) calculation0.getResult(), this.sign == ConstantRegion.MINUS_SIGN ? -(double) calculation1.getResult() : (double) calculation1.getResult(),
                this.getCalculationName()
        );
    }

    @Override
    public CalculationComplexResults calculationCache(boolean isCopy) {
        return (CalculationComplexResults) super.calculationCache(isCopy);
    }

    @Override
    public CalculationComplexResults calculationBigDecimalsCache(boolean isCopy) {
        return (CalculationComplexResults) super.calculationBigDecimalsCache(isCopy);
    }

    /**
     * 获取实部
     *
     * @return 将当前复数对象中的实部获取到，并返回实部的表达式对象。
     * <p>
     * Retrieve the real part of the current complex object and return the expression object for the real part.
     */
    public FunctionExpression getFunctionExpression1() {
        return functionExpression1;
    }

    /**
     * 获取虚部
     *
     * @return 将当前复数对象中的虚部获取到，并返回虚部的表达式对象。
     * <p>
     * Retrieve the imaginary part of the current complex object and return the expression object for the imaginary part.
     */
    public FunctionExpression getFunctionExpression2() {
        return functionExpression2;
    }
}
