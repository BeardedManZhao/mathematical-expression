package io.github.beardedManZhao.mathematicalExpression.core.calculation.number;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationComplexResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.ComplexExpression;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.PrimitiveIterator;

import static io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement.FUNCTION_FORMULA_CALCULATION_2_NAME;

/**
 * 复数计算组件，此组件中支持对于复数的表达式的运算！
 * <p>
 * Complex calculation component, which supports operations on complex expressions!
 *
 * @author zhao
 */
public class ComplexCalculation extends NumberCalculation implements CompileCalculation {

    protected final static FunctionFormulaCalculation2 FUNCTION_FORMULA_CALCULATION_2 = FunctionFormulaCalculation2.getInstance(FUNCTION_FORMULA_CALCULATION_2_NAME);

    protected ComplexCalculation(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析比较运算公式的计算组件对象
     */
    public static ComplexCalculation getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof ComplexCalculation) {
                return (ComplexCalculation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 ComplexCalculation 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            ComplexCalculation BooleanCalculation2 = new ComplexCalculation(CalculationName);
            CalculationManagement.register(BooleanCalculation2, false);
            return BooleanCalculation2;
        }
    }

    /**
     * 获取复数表达式的计算结果
     *
     * @param Formula  需要被计算的表达式
     * @param explain  实部的计算结果
     * @param explain1 虚部的计算结果
     * @param name     计算结果的名称
     * @return LogResults
     */
    private static LogResults getLogResults(String Formula, LogResults explain, LogResults explain1, String name) {
        // 生成名字
        final String s = "start" + System.currentTimeMillis();
        final LogResults logResults = new LogResults(s, explain, explain1);
        logResults.setNameJoin(false);
        logResults.setPrefix(s + "(\"" + Formula + "\")");
        logResults.setResult(
                new CalculationComplexResults(-1, (double) explain.getResult(), (double) explain1.getResult(), name)
        );
        logResults.put("result", logResults.getResult().toString());
        return logResults;
    }

    @Override
    public String formatStr(String string) {
        return StrUtils.removeEmpty(string);
    }

    @Override
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        return Mathematical_Expression.Options.isUseBigDecimal() ?
                this.compile(Formula, formatRequired).calculationBigDecimalsCache(false) :
                this.compile(Formula, formatRequired).calculationCache(false);
    }

    @Override
    public ComplexExpression compile(String Formula, boolean formatRequired) {
        if (formatRequired) {
            Formula = formatStr(Formula);
        }
        return ComplexExpression.compile(Formula, this.getName());
    }

    @Override
    public ComplexExpression compileBigDecimal(String Formula, boolean formatRequired) {
        if (formatRequired) {
            Formula = formatStr(Formula);
        }
        return this.compile(Formula, formatRequired);
    }

    @Override
    public void check(String expression) throws WrongFormat {
        // 首先准备计数器，当前面的右括号与左括号一样的时候 此数值应为0
        int count = 0;
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
                        // 检查实部
                        FUNCTION_FORMULA_CALCULATION_2.check(stringBuilder.toString());
                        // 然后编译出剩下的部分 但是要在这里找一下 i 的位置
                        final int i = expression.lastIndexOf('i');
                        // 检查虚部
                        if (i == -1 || expression.length() <= stringBuilder.length()) {
                            // 找不到 i 或者 总体长度小于等于实部长度 这代表 这个表达式不是一个复数
                            throw new WrongFormat("The imaginary part of the complex number is not found. error => " + expression.substring(stringBuilder.length()) + " ﹏+/-﹏<imaginary>﹏i");
                        }
                        FUNCTION_FORMULA_CALCULATION_2.check(expression.substring(stringBuilder.length(), i));
                        // 结束循环
                        return;
                    }
            }
            stringBuilder.append(c1);
        }
        throw new RuntimeException(expression + " is not a complex expression.");
    }

    @Override
    public LogResults explain(String Formula, boolean formatRequired) {
        // 首先准备计数器，当前面的右括号与左括号一样的时候 此数值应为0
        int count = 0;
        final StringBuilder stringBuilder = new StringBuilder(Formula.length());
        final PrimitiveIterator.OfInt iterator = Formula.chars().iterator();
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
                        // 处理实部
                        final LogResults explain = FUNCTION_FORMULA_CALCULATION_2.explain(stringBuilder.toString(), formatRequired);
                        // 然后编译出剩下的部分 但是要在这里找一下 i 的位置
                        final int i = Formula.lastIndexOf('i');
                        // 检查虚部
                        if (i == -1 || Formula.length() <= stringBuilder.length()) {
                            // 找不到 i 或者 总体长度小于等于实部长度 这代表 这个表达式不是一个复数
                            throw new UnsupportedOperationException("The imaginary part of the complex number is not found. error => " + Formula.substring(stringBuilder.length()) + " ﹏+/-﹏<imaginary>﹏i");
                        }
                        // 结束循环
                        return getLogResults(Formula, explain, FUNCTION_FORMULA_CALCULATION_2.explain(Formula.substring(stringBuilder.length(), i), formatRequired), this.getName());
                    }
            }
            stringBuilder.append(c1);
        }
        throw new RuntimeException(Formula + " is not a complex expression.");
    }
}
