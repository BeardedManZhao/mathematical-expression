package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.JvmExpressionFunction;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 求解器表达式类，用于解析方程表达式。可以修改 已知数值
 *
 * @author 赵凌宇
 */
public class EquationSolverExpression extends EquationSolver implements CloneExpression {

    private static final Pattern numberPattern = Pattern.compile("\\d+|x");
    private final HashSet<Integer> unkNameIndex;
    private final JvmExpression jvmExpression;

    /**
     * 私有构造函数，通过表达式字符串与结果值初始化求解器。
     *
     * @param exprString   方程左侧表达式字符串（关于 x）
     * @param exprResult   方程右侧常数字符串
     * @param name         计算器名称，用于动态编译函数命名
     * @param function     已编译的函数 这个就是方程左边表达式的编译
     * @param unkNameIndex 方程未知数参数索引，这个索引中标记的参数是未知数，设置数值准备复用的时候可以使用这个校验当前设置的是否是一个未知数，我们不能设置一个未知数！
     * @throws IllegalArgumentException 如果右侧值无法解析为数字
     */
    protected EquationSolverExpression(String exprString, double exprResult, String name, HashSet<Integer> unkNameIndex, JvmExpression function) {
        super(exprString, exprResult, name, function.getJvmExpressionFunction());
        this.unkNameIndex = unkNameIndex;
        this.jvmExpression = function;
        autoDetectInterval();
    }


    /**
     * 编译方程求解器实例。表达式必须为 "左侧表达式=右侧数值" 格式。
     *
     * @param expression      含有等号的方程字符串
     * @param calculationName 实例名称，用于内部函数编译
     * @return 已初始化并完成区间检测的 {@link EquationSolver} 实例
     * @throws IllegalArgumentException 方程格式不合法时抛出
     */
    public static EquationSolverExpression compile(String expression, String calculationName) {
        ArrayList<String> parts = StrUtils.splitByChar(expression, '=');
        if (parts.size() != 2) {
            throw new IllegalArgumentException("方程表达式格式应为 '表达式=值'，实际：" + expression);
        }
        final String s = parts.get(0);
        final HashSet<Integer> unkNameIndex = new HashSet<>();
        ArrayList<Double> allNumber = new ArrayList<>();
        // 表达式的签名 fun_XXX(x1,x2...)
        final StringBuilder params = new StringBuilder("funLingYuZhao" + StrUtils.randomString(8)).append('(');
        // 表达式体 x1 + x2 ...
        final StringBuilder stringBuilder = new StringBuilder(") = ");
        int count = -1;
        int lastIndex = 0;
        final Matcher matcher = numberPattern.matcher(s);
        while (matcher.find()) {
            final String group = matcher.group();
            final String p = "param" + ++count;
            if ("x".equals(group)) {
                unkNameIndex.add(count);
                allNumber.add(0.0);
            } else {
                allNumber.add(Double.parseDouble(group));
            }
            params.append(p).append(',');
            // 构建表达式体
            stringBuilder.append(s, lastIndex, matcher.start()).append(p);
            lastIndex = matcher.end();
        }
        params.deleteCharAt(params.length() - 1);
        final String string = params.append(stringBuilder).append(s, lastIndex, s.length()).toString();
        JvmExpressionFunction parse = JvmExpressionFunction.parse(string);
        // 开始编译
        return new EquationSolverExpression(
                s, Double.parseDouble(parts.get(1)),
                calculationName,
                unkNameIndex,
                new JvmExpression(
                        calculationName,
                        allNumber.stream().mapToDouble(Double::doubleValue).toArray(),
                        parse,
                        string
                )
        );
    }

    public JvmExpression getJvmExpression() {
        return jvmExpression;
    }

    /**
     * 将表达式中一些已知数值进行修改，但是不能修改未知数索引对应的数值
     *
     * @param index 需要被修改的索引 注意这个索引处指向的应是已知数值索引
     * @param value 修改后的数值
     */
    public void setKnownNumber(final int index, final double value) {
        if (indexIsUnKnownNumber(index)) {
            throw new IllegalArgumentException("未知数索引不能被修改！");
        }
        jvmExpression.setParamNumber(index, value);
    }

    /**
     * 将表达式中一些未知数值进行修改，请注意 当您修改了未知数的索引之后，其将无法参与求解！！！
     *
     * @param index 需要被修改的索引 注意这个索引处指向的应是已知数值索引
     * @param value 修改后的数值
     */
    public void setUnKnownNumber(final int index, final double value) {
        if (this.unkNameIndex.remove(index)) {
            jvmExpression.setParamNumber(index, value);
            return;
        }
        final String p = index + ", " + value;
        throw new IllegalArgumentException("您调用的方法是 setUnKnownNumber(" + p + ")，这是用于将一个" + index + "索引的未知数设置为已知的数值，让其不再参与求解，但是它事实上是一个已经知道的数值！如果您期望将已知数值修改，请调用 setKnownNumber(" + p + ")");
    }

    /**
     * 判断当前索引是否是未知数索引
     *
     * @param index 需要被判断的索引
     * @return true 表示是未知数索引，false 表示不是未知数索引
     */
    public boolean indexIsUnKnownNumber(final int index) {
        return unkNameIndex.contains(index);
    }

    /**
     * 在指定 x 值处评估方程左侧表达式。
     *
     * @param x 自变量值
     * @return 表达式在 x 处的计算结果
     */
    @Override
    protected double eval(double x) {
        for (Integer nameIndex : unkNameIndex) {
            this.jvmExpression.allNumber[nameIndex] = x;
        }
        return super.function.run(this.jvmExpression.allNumber);
    }

    @Override
    public EquationSolverExpression cloneExpression() {
        return new EquationSolverExpression(
                this.getExpressionStr(),
                this.result,
                this.getCalculationName() + "_clone",
                this.unkNameIndex,
                this.jvmExpression.cloneExpression()
        );
    }
}
