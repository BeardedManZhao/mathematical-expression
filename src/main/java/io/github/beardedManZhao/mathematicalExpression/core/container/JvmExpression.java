package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.JvmExpressionFunction;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

/**
 * JvmExpression 表达式对象
 *
 * @author 赵凌宇
 */
public class JvmExpression extends NameExpression implements CloneExpression {

    private final static Pattern numberPattern = Pattern.compile("\\d+");
    /**
     * 存储是每个操作数的值
     */
    final double[] allNumber;

    private final JvmExpressionFunction jvmExpressionFunction;

    JvmExpression(String calculationName, double[] allNumber, JvmExpressionFunction jvmExpressionFunction, String expression) {
        super(expression, calculationName);
        this.allNumber = allNumber;
        this.jvmExpressionFunction = jvmExpressionFunction;
    }

    public static JvmExpression compile(String expression) {
        return compile(expression, expression);
    }

    public static JvmExpression compile(String expression, String calculationName) {
        ArrayList<Double> allNumber = new ArrayList<>();
        // 表达式的签名 fun_XXX(x1,x2...)
        final StringBuilder params = new StringBuilder("funLingYuZhao" + StrUtils.randomString(8)).append('(');
        // 表达式体 x1 + x2 ...
        final StringBuilder stringBuilder = new StringBuilder(") = ");
        int count = -1;
        int lastIndex = 0;
        final Matcher matcher = numberPattern.matcher(expression);
        while (matcher.find()) {
            final String group = matcher.group();
            allNumber.add(Double.parseDouble(group));
            final String p = "param" + ++count;
            params.append(p).append(',');
            // 构建表达式体
            stringBuilder.append(expression, lastIndex, matcher.start()).append(p);
            lastIndex = matcher.end();
        }
        params.deleteCharAt(params.length() - 1);
        final String string = params.append(stringBuilder).append(expression, lastIndex, expression.length()).toString();
        // 开始编译
        return new JvmExpression(
                calculationName,
                allNumber.stream().mapToDouble(Double::doubleValue).toArray(),
                JvmExpressionFunction.parse(string),
                string
        );
    }

    public DoubleStream iterator() {
        return Arrays.stream(this.allNumber);
    }

    public double getParamNumber(int index) {
        return this.allNumber[index];
    }

    public void setParamNumber(int index, double value) {
        this.allNumber[index] = value;
    }

    public int getLength() {
        return this.allNumber.length;
    }

    public JvmExpressionFunction getJvmExpressionFunction() {
        return jvmExpressionFunction;
    }

    /**
     * 获取当前计算表达式是否为 BigDecimal 类型的计算表达式
     * <p>
     * Get whether the current calculation expression is a BigDecimal type calculation expression
     *
     * @return 当前计算表达式是否为 BigDecimal 类型的计算表达式
     * <p>
     * Whether the current calculation expression is a BigDecimal type calculation expression
     */
    @Override
    public boolean isBigDecimal() {
        return false;
    }

    /**
     * 如果当前的计算表达式对象支持非高精度的计算模式，则返回 true
     *
     * @return If the current calculation expression object supports non high-precision calculation modes, return true
     */
    @Override
    public boolean isUnBigDecimal() {
        return true;
    }

    /**
     * 转换计算表达式为多精度支持形式
     * <p>
     * Transforms the current calculation expression into a form that supports both high precision and standard precision evaluations.
     */
    @Override
    public void convertToMultiPrecisionSupported() {
        throw new UnsupportedOperationException("JvmExpression does not support BigDecimal mode.");
    }

    /**
     * 将当前计算表达式类的结果计算出来！
     * <p>
     * Calculate the result of the current calculation expression class!
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    @Override
    public CalculationNumberResults calculation(boolean isCopy) {
        double run = this.jvmExpressionFunction.run(this.allNumber);
        return new CalculationNumberResults(1, run, this.getExpressionStr());
    }

    /**
     * 将当前计算表达式类的结果计算出来！
     * <p>
     * Calculate the result of the current calculation expression class!
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    @Override
    public CalculationNumberResults calculationBigDecimals(boolean isCopy) {
        throw new UnsupportedOperationException("JvmExpression does not support BigDecimal mode.");
    }

    @Override
    public JvmExpression cloneExpression() {
        return new JvmExpression(
                this.getCalculationName() + "_clone",
                this.allNumber.clone(),
                this.jvmExpressionFunction,
                this.getExpressionStr()
        );
    }

    /**
     * @return 带有参数的具体的参数表达式
     */
    public String getExpressionStrAndNumber() {
        return this.jvmExpressionFunction.explain(this.allNumber);
    }
}
