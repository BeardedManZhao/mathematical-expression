package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.CalculationBigDecimalResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.utils.CalculationOptimized;
import io.github.beardedManZhao.mathematicalExpression.utils.NumberUtils;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

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

    private static final long serialVersionUID = "PrefixExpression_1".hashCode();
    // 创建操作数栈
    private final Stack<Character> characterStackR;
    private Stack<Double> doubleStackR;
    private Stack<BigDecimal> bigDecimalsR;
    /**
     * isBigDecimal 是否是一个 支持高精度模式的计算表达式
     * isUnBigDecimal 是否是一个 支持非高精度模式的计算表达式
     */
    private boolean isBigDecimal, isUnBigDecimal;

    public PrefixExpression(Stack<BigDecimal> bigDecimalStack, Stack<Double> doubleStack, Stack<Character> characterStack, String expression, String calculationName) {
        super(expression, calculationName);
        this.doubleStackR = doubleStack;
        this.bigDecimalsR = bigDecimalStack;
        this.characterStackR = characterStack;
        this.isBigDecimal = bigDecimalStack != null;
        this.isUnBigDecimal = !this.isBigDecimal;
    }

    /**
     * 更新当前的操作符类型 此函数为内部函数~ 请勿直接调用！！ 用于在上一个是操作符的时候，解析数值字符串并存储。
     *
     * @param stringBuilder 结果缓冲
     * @param c             被判断的操作字符
     */
    public static void isBackIsOpt(StringBuilder stringBuilder, char c) {
        if (StrUtils.IsANumber(c)) {
            // 如果是数值的某一位，就将数值存储到变量中
            stringBuilder.append(c);
        }
        switch (c) {
            case ConstantRegion.FACTORIAL_SIGN:
            case ConstantRegion.DECIMAL_POINT:
            case ConstantRegion.MINUS_SIGN:
                // 如果是数值的某一位，就将数值存储到变量中
                stringBuilder.append(c);
        }
    }

    /**
     * 编译一个数学表达式 并返回表达式的对象
     * <p>
     * Compile a mathematical expression and return the object of the expression
     *
     * @param newFormula      表达式的字符串格式 最好是被格式化之后的！
     *                        <p>
     *                        The string format of the expression is best formatted!
     * @param calculationName 计算组件的名称，会将此参数做为计算表达式的名称，此计算表达式计算出来的结果的名称都会使用此名称。
     *                        <p>
     *                        The name of the calculation component will use this parameter as the name of the calculation expression, and the name of the result calculated by this calculation expression will use this name.
     * @return 表达式的对象，其中存储着有关数学表达式对象的操作信息。
     * <p>
     * The object of an expression, which stores operational information about mathematical expression objects.
     */
    public static PrefixExpression compile(String newFormula, String calculationName) {
        final boolean useCache = Mathematical_Expression.Options.isUseCache();
        if (useCache) {
            // 判断下缓存有没有
            final PackExpression cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(newFormula);
            if (cacheCalculation != null) {
                return (PrefixExpression) cacheCalculation.dismantling();
            }
        }
        // 到这里就代表没有启动缓存 或者 缓存不到 开始解析 首先 创建操作符栈
        final Stack<Double> doubleStack = new Stack<>();
        // 创建操作数栈
        final Stack<Character> characterStack = new Stack<>();
        // 开始格式化，将符号与操作数进行分类
        int length = newFormula.length();
        final StringBuilder stringBuilder = new StringBuilder(length);
        // 创建标记点 标记上一个是否是操作符
        boolean backIsOpt = true;
        for (int i = 0; i < length; i++) {
            char c = newFormula.charAt(i);
            if (!backIsOpt && StrUtils.IsAnOperator(c)) {
                backIsOpt = true;
                // 如果上一个不是操作符，且当前是操作符，就先将上一个数值计算出来
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
                continue;
            }
            PrefixExpression.isBackIsOpt(stringBuilder, c);
            backIsOpt = false;
        }
        doubleStack.push(StrUtils.stringToDouble(stringBuilder.toString()));
        final PrefixExpression prefixExpression = new PrefixExpression(null, doubleStack, characterStack, newFormula, calculationName);
        // 如果启用了缓存就缓存
        if (useCache) {
            Mathematical_Expression.Options.cacheCalculation(newFormula, new PackExpression(null, prefixExpression, calculationName));
        }
        return prefixExpression;
    }

    /**
     * 编译一个数学表达式 并返回表达式的对象
     * <p>
     * Compile a mathematical expression and return the object of the expression
     *
     * @param newFormula      表达式的字符串格式 最好是被格式化之后的！
     *                        <p>
     *                        The string format of the expression is best formatted!
     * @param calculationName 计算组件的名称，会将此参数做为计算表达式的名称，此计算表达式计算出来的结果的名称都会使用此名称。
     *                        <p>
     *                        The name of the calculation component will use this parameter as the name of the calculation expression, and the name of the result calculated by this calculation expression will use this name.
     * @return 表达式的对象，其中存储着有关数学表达式对象的操作信息。
     * <p>
     * The object of an expression, which stores operational information about mathematical expression objects.
     */
    public static PrefixExpression compileBigDecimal(String newFormula, String calculationName) {
        final boolean useCache = Mathematical_Expression.Options.isUseCache();
        if (useCache) {
            // 判断下缓存有没有
            final PackExpression cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(newFormula + "uB");
            if (cacheCalculation != null) {
                return (PrefixExpression) cacheCalculation.dismantling();
            }
        }
        // 到这里就代表没有启动缓存 或者 缓存不到 开始解析 首先 创建操作符栈
        final Stack<BigDecimal> doubleStack = new Stack<>();
        // 创建操作数栈
        final Stack<Character> characterStack = new Stack<>();
        // 开始格式化，将符号与操作数进行分类
        int length = newFormula.length();
        final StringBuilder stringBuilder = new StringBuilder(length);
        // 创建标记点 标记上一个是否是操作符
        boolean backIsOpt = true;
        for (int i = 0; i < length; i++) {
            char c = newFormula.charAt(i);
            if (!backIsOpt && StrUtils.IsAnOperator(c)) {
                backIsOpt = true;
                // 如果上一个不是操作符，且当前是操作符，就先将上一个数值计算出来
                BigDecimal number = StrUtils.stringToBigDecimal(stringBuilder.toString());
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
                        doubleStack.push(CalculationOptimized.calculation(top, doubleStack.pop(), number));
                        characterStack.push(c);
                    } else {
                        // 反之就将当前运算符提供到栈顶
                        characterStack.push(c);
                        doubleStack.push(number);
                    }
                }
                // 清理所有的字符缓冲
                stringBuilder.delete(0, stringBuilder.length());
                continue;
            }
            PrefixExpression.isBackIsOpt(stringBuilder, c);
            backIsOpt = false;
        }
        doubleStack.push(StrUtils.stringToBigDecimal(stringBuilder.toString()));
        final PrefixExpression prefixExpression = new PrefixExpression(doubleStack, null, characterStack, newFormula, calculationName);
        // 如果启用了缓存就缓存
        if (useCache) {
            Mathematical_Expression.Options.cacheCalculation(newFormula + "uB", new PackExpression(null, prefixExpression, calculationName));
        }
        return prefixExpression;
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
        if (this.isAvailable()) {
            return isCopy ? (Stack<Double>) doubleStackR.clone() : doubleStackR;
        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
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
        if (this.isAvailable()) {
            return isCopy ? (Stack<BigDecimal>) bigDecimalsR.clone() : bigDecimalsR;
        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
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
        if (this.isAvailable()) {
            return isCopy ? (Stack<Character>) characterStackR.clone() : characterStackR;

        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());

    }

    @Override
    public boolean isBigDecimal() {
        return this.isBigDecimal;
    }

    @Override
    public boolean isUnBigDecimal() {
        return this.isUnBigDecimal;
    }

    @Override
    public void convertToMultiPrecisionSupported() {
        // 首先判断当前的状态
        if (this.isBigDecimal()) {
            // 代表是精度模式，看是否支持非精度，如果不支持就给非精度模式的支持开通
            if (doubleStackR != null) {
                // 代表都支持 不需要进行操作
                return;
            }
            // 开通非精度模式
            this.doubleStackR = new Stack<>();
            for (BigDecimal bigDecimal : this.getBigDecimalsR(false)) {
                this.doubleStackR.push(bigDecimal.doubleValue());
            }
            this.isUnBigDecimal = true;
            return;
        }
        // 代表是非精度模式，看是否支持精度，如果不支持就给精度模式的支持开通
        if (bigDecimalsR != null) {
            // 代表都支持 不需要进行操作
            return;
        }
        this.bigDecimalsR = new Stack<>();
        for (Double aDouble : this.getDoubleStack(false)) {
            this.bigDecimalsR.push(new BigDecimal(aDouble));
        }
        this.isBigDecimal = true;
    }

    @Override
    public CalculationNumberResults calculation(boolean isCopy) {
        if (!this.isUnBigDecimal()) {
            throw new UnsupportedOperationException("此表达式是一个高精度的类型，请您调用 calculationBigDecimals(" + isCopy + ") or calculationBigDecimalsCache(" + isCopy + ")\nThis Expression is of high precision type. Please call calculationBigDecimals(" + isCopy + ") or calculationBigDecimalsCache(" + isCopy + ')');
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
        if (!this.isBigDecimal()) {
            throw new UnsupportedOperationException("此表达式不是一个高精度的类型，请您调用 calculation(" + isCopy + ") or calculationCache(" + isCopy + ")\nThis expression is not of a high precision type. Please call calculation(" + isCopy + ") or calculation(" + isCopy + ')');
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
