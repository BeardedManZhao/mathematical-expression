package io.github.beardedManZhao.mathematicalExpression.core.calculation.number;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import io.github.beardedManZhao.mathematicalExpression.utils.CalculationOptimized;
import io.github.beardedManZhao.mathematicalExpression.utils.NumberUtils;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;
import top.lingyuzhao.varFormatter.utils.DataObj;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 解析一个无括号的数学计算公式的组件，针对不含括号的计算公式，该组件可以提供计算支持
 * <p>
 * Parse a component of mathematical calculation formula without brackets. This component can provide calculation support for calculation formula without brackets
 *
 * @author zhao
 */
public final class PrefixExpressionOperation extends NumberCalculation {

    /**
     * 正负号，用于将所有的 +- 替换成为 -
     */
    private final static Pattern SIGN_PATTERN = Pattern.compile(ConstantRegion.REGULAR_ADDITION_SUBTRACTION_AMBIGUITY);

    private PrefixExpressionOperation(String name) {
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
     * 更新当前的操作符类型
     *
     * @param stringBuilder 结果缓冲
     * @param backIsOpt     上一个字符是否是操作符
     * @param c             被判断的操作字符
     * @return 新的结果
     */
    private static boolean isBackIsOpt(StringBuilder stringBuilder, boolean backIsOpt, char c) {
        if (StrUtils.IsANumber(c)) {
            // 如果是数值的某一位，就将数值存储到变量中
            stringBuilder.append(c);
            backIsOpt = false;
            return backIsOpt;
        }
        switch (c) {
            case ConstantRegion.FACTORIAL_SIGN:
            case ConstantRegion.DECIMAL_POINT:
            case ConstantRegion.MINUS_SIGN:
                // 如果是数值的某一位，就将数值存储到变量中
                stringBuilder.append(c);
                backIsOpt = false;
        }
        return backIsOpt;
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
        int last = string.length() - 1;
        char lastChar = string.charAt(last);
        while (lastChar == ConstantRegion.EMPTY) {
            lastChar = string.charAt(--last);
        }
        if (StrUtils.IsANumber(lastChar)) {
            boolean is = false;
            for (int i = 0; i < last; i++) {
                char c = string.charAt(i);
                if (c == ConstantRegion.LEFT_BRACKET || c == ConstantRegion.RIGHT_BRACKET) {
                    throw new WrongFormat("组件" + this.Name + "只能解析无括号的数学表达式，请将表达式中的括号去除。\nThe component " + this.Name + " can only resolve mathematical expressions without parentheses. Please remove the parentheses from the expressions.");
                }
                if (!StrUtils.IsANumber(c) && c != ConstantRegion.DECIMAL_POINT && c != ConstantRegion.EMPTY) {
                    if (!StrUtils.IsAnOperator(c)) {
                        throw new WrongFormat("解析表达式的时候出现了未知符号!!!\nUnknown symbol appears when parsing expression!!!\nWrong format => [" + c + "] from " + string);
                    } else {
                        if (c != ConstantRegion.MINUS_SIGN && is)
                            throw new WrongFormat("您的数学表达式不正确，缺失了一个运算数值或多出了一个运算符。ERROR => " + string);
                        is = true;
                        continue;
                    }
                }
                is = false;
            }
        } else {
            throw new WrongFormat("数学表达式的最后一个字符应是一个数值。\nThe last character of a mathematical expression should be a numeric value.\nERROR => " + lastChar);
        }
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return SIGN_PATTERN.matcher(string).replaceAll(ConstantRegion.MINUS_SIGN_STR) + ConstantRegion.PLUS_SIGN + '0';
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
        final String newFormula;
        if (formatRequired) {
            newFormula = formatStr(Formula);
        } else {
            newFormula = Formula;
        }
        if (Mathematical_Expression.Options.isUseBigDecimal()) {
            return this.calculationBigDecimal(newFormula);
        }
        // 创建操作符栈
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
            backIsOpt = isBackIsOpt(stringBuilder, backIsOpt, c);
        }
        doubleStack.push(StrUtils.stringToDouble(stringBuilder.toString()));
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
        return new CalculationNumberResults(size - 1, res, this.Name);
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param newFormula 被计算的表达式，要求返回值是一个数值。
     *                   <p>
     *                   The returned value of the evaluated expression is required to be a numeric value.
     * @return 数值结果集对象，其中保存着每一步的操作数据，以及最终结果数值
     * <p>
     * Numerical result set object, which stores the operation data of each step and the final result value
     */
    private CalculationNumberResults calculationBigDecimal(final String newFormula) {
        // 创建操作符栈
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
            backIsOpt = isBackIsOpt(stringBuilder, backIsOpt, c);
        }
        doubleStack.push(StrUtils.stringToBigDecimal(stringBuilder.toString()));
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
        return new CalculationBigDecimalResults(sizeD2, res, this.Name);
    }

    @Override
    public LogResults explain(String Formula, boolean formatRequired) {
        final String newFormula;
        if (formatRequired) {
            newFormula = formatStr(Formula);
        } else {
            newFormula = Formula;
        }
        // 设置当前公式的名字
        final String s1 = "f_" + Formula.hashCode();
        // 创建操作符栈
        final Stack<Double> doubleStack = new Stack<>();
        // 创建操作数栈
        final Stack<Character> characterStack = new Stack<>();
        // 开始格式化，将符号与操作数进行分类
        int length = newFormula.length();
        final StringBuilder stringBuilder = new StringBuilder(length);
        // 准备日志记录
        LogResults logResults = new LogResults(s1);
        // 将当前公式的变量 做为前置语句
        logResults.setPrefix(s1 + "(\"" + newFormula + "\")");
        // 准备一个指针 指向日志记录的不同层级 在这里首先是日志的根节点
        DataObj dataObj = new DataObj(s1 + "_优先");
        logResults.put(dataObj);
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
                        final Double pop = doubleStack.pop();
                        final double calculation = NumberUtils.calculation(top, pop, number);
                        doubleStack.push(calculation);
                        characterStack.push(c);
                        // 在这里进行标记，这里就是优先级较高的
                        final String s = pop + " " + top + " " + number;
                        final String s2 = "f_" + s.hashCode();
                        final DataObj dataObj1 = new DataObj(s2 + "_计算");
                        dataObj1.setPrefix(s2 + "(\"" + s + "\")");
                        dataObj1.put(s2, calculation);
                        dataObj.put(dataObj1);
                        // 更新层级
                        dataObj = dataObj1;
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
            backIsOpt = isBackIsOpt(stringBuilder, backIsOpt, c);
        }
        doubleStack.push(StrUtils.stringToDouble(stringBuilder.toString()));
        double res = doubleStack.firstElement();
        char back;
        final int size = doubleStack.size();
        // 开始计算
        final DataObj dataObj2 = new DataObj(s1 + "_最终");
        dataObj.put(dataObj2);
        dataObj = dataObj2;
        logResults.put(dataObj);
        final int sizeD2 = size >> 1;
        for (int i = 1, offset = 0; i < size && offset < sizeD2; ++offset, ++i) {
            // 更新操作符
            back = characterStack.get(offset);
            // 获取操作数并计算结果
            final Double v = doubleStack.get(i);
            final String s2 = res + " " + back + " " + v;
            final String s3 = "f_" + s2.hashCode();
            final DataObj dataObj1 = new DataObj(s3 + "_计算");
            dataObj1.setPrefix(s3 + "(\"" + s2 + "\")");
            dataObj.put(dataObj1);
            res = NumberUtils.calculation(back, res, v);
            dataObj1.put(s3, res);
            dataObj = dataObj1;
        }
        dataObj.put("result", res);
        logResults.setResult(res);
        // 返回结果
        return logResults;
    }
}
