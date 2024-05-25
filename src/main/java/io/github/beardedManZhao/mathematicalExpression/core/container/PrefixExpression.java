package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.CalculationBigDecimalResults;
import io.github.beardedManZhao.mathematicalExpression.utils.CalculationOptimized;
import io.github.beardedManZhao.mathematicalExpression.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 无括号的数学表达式计算组件。
 * <p>
 * Mathematical expression calculation component without parentheses.
 *
 * @author zhao
 */
@SuppressWarnings("unchecked")
public class PrefixExpression extends NameExpression {

    private static final long serialVersionUID = "PrefixExpression".hashCode();

    private         // 创建操作符栈
    final Stack<Double> doubleStackR;
    private final Stack<BigDecimal> bigDecimalsR;
    // 创建操作数栈
    private final Stack<Character> characterStackR;

    /**
     * 是否是一个 高精度模式的计算表达式
     */
    private final boolean isBigDecimal;

    public PrefixExpression(Stack<BigDecimal> bigDecimalStack, Stack<Double> doubleStack, Stack<Character> characterStack, String expression, String calculationName) {
        super(expression, calculationName);
        this.doubleStackR = doubleStack;
        this.bigDecimalsR = bigDecimalStack;
        this.characterStackR = characterStack;
        this.isBigDecimal = bigDecimalStack != null;
    }

    /**
     * 获取到此表达式中的运算符栈
     *
     * @param isCopy 如果您需要多次调用此函数请设置为 true。请注意，如果您设置为不复制，则此函数在您调用了 calculation 函数后将不再可用！
     *               <p>
     *               If you need to call this function multiple times, please set it to true. Please note that if you set it to not copy, this function will no longer be available after you call the calculation function!
     * @return 运算符栈的对象 如果在 isCopy 设置为 false 的情况下，此函数将返回源对象，请您不要修改，如果需要修改可以设置为 true。
     * <p>
     * If the object of the operator stack is set to false in isCopy, this function will return the source object. Please do not modify it. If you need to modify it, you can set it to true.
     */
    public Stack<Double> getDoubleStack(boolean isCopy) {
        if (!this.isAvailable()) {
            throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
        }
        return isCopy ? (Stack<Double>) doubleStackR.clone() : doubleStackR;
    }

    /**
     * 获取到此表达式中的运算符栈
     *
     * @param isCopy 如果您需要多次调用此函数请设置为 true。请注意，如果您设置为不复制，则此函数在您调用了 calculation 函数后将不再可用！
     *               <p>
     *               If you need to call this function multiple times, please set it to true. Please note that if you set it to not copy, this function will no longer be available after you call the calculation function!
     * @return 运算符栈的对象 如果在 isCopy 设置为 false 的情况下，此函数将返回源对象，请您不要修改，如果需要修改可以设置为 true。
     * <p>
     * If the object of the operator stack is set to false in isCopy, this function will return the source object. Please do not modify it. If you need to modify it, you can set it to true.
     */
    public Stack<BigDecimal> getBigDecimalsR(boolean isCopy) {
        if (!this.isAvailable()) {
            throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
        }
        return isCopy ? (Stack<BigDecimal>) bigDecimalsR.clone() : bigDecimalsR;
    }

    /**
     * 获取到此表达式中的操作符栈
     *
     * @param isCopy 如果您需要多次调用此函数请设置为 true。请注意，如果您设置为不复制，则此函数在您调用了 calculation 函数后将不再可用！
     *               <p>
     *               If you need to call this function multiple times, please set it to true. Please note that if you set it to not copy, this function will no longer be available after you call the calculation function!
     * @return 运算符栈的对象 如果在 isCopy 设置为 false 的情况下，此函数将返回源对象，请您不要修改，如果需要修改可以设置为 true。
     * <p>
     * If the object of the operator stack is set to false in isCopy, this function will return the source object. Please do not modify it. If you need to modify it, you can set it to true.
     */
    public Stack<Character> getCharacterStack(boolean isCopy) {
        if (!this.isAvailable()) {
            throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
        }
        return isCopy ? (Stack<Character>) characterStackR.clone() : characterStackR;
    }

    @Override
    public boolean isBigDecimal() {
        return this.isBigDecimal;
    }

    @Override
    public CalculationNumberResults calculation(boolean isCopy) {
        if (this.isBigDecimal) {
            throw new UnsupportedOperationException("此表达式是一个高精度的类型，请您调用 calculationBigDecimals(" + isCopy + ")\nThis Expression is of high precision type. Please call calculationBigDecimals(" + isCopy + ')');
        }
        final Stack<Double> doubleStack = getDoubleStack(isCopy);
        final Stack<Character> characterStack = getCharacterStack(isCopy);
        double res = doubleStack.firstElement();
        char back;
        final int size = doubleStack.size();
        // 开始计算
        final int sizeD2 = size >> 1;
        for (int i = 1, offset = 0; i < size && offset < sizeD2; ++offset, ++i) {
            // 更新操作符
            back = characterStack.get(offset);
            // 获取操作数并计算结果
            res = NumberUtils.calculation(back, res, doubleStack.get(i));
        }
        // 返回结果
        return new CalculationNumberResults(size - 1, res, this.getCalculationName());
    }

    @Override
    public CalculationNumberResults calculationBigDecimals(boolean isCopy) {
        if (!this.isBigDecimal) {
            throw new UnsupportedOperationException("此表达式不是一个高精度的类型，请您调用 calculation(" + isCopy + ")\nThis expression is not of a high precision type. Please call calculation(" + isCopy + ')');
        }
        final Stack<BigDecimal> doubleStack = getBigDecimalsR(isCopy);
        final Stack<Character> characterStack = getCharacterStack(isCopy);
        BigDecimal res = doubleStack.firstElement();
        char back;
        final int size = doubleStack.size();
        // 开始计算
        final int sizeD2 = size >> 1;
        for (int i = 1, offset = 0; i < size && offset < sizeD2; ++offset, ++i) {
            // 更新操作符
            back = characterStack.get(offset);
            // 获取操作数并计算结果
            res = CalculationOptimized.calculation(back, res, doubleStack.get(i));
        }
        // 返回结果
        return new CalculationBigDecimalResults(sizeD2, res, this.getCalculationName());
    }

    @Override
    public CalculationNumberResults calculationCache(boolean isCopy) {
        return (CalculationNumberResults) super.calculationCache(isCopy);
    }

    @Override
    public CalculationNumberResults calculationBigDecimalsCache(boolean isCopy) {
        return (CalculationNumberResults) super.calculationBigDecimalsCache(isCopy);
    }
}
