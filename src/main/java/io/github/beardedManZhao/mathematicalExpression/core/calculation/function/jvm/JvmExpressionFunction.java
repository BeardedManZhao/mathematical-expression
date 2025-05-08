package io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ExpressionFunction;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 基于 JVM 动态编译的多参数数值函数。
 * 支持自定义静态函数注册。
 * PS: 此类速度极快，和直接调用Java代码无异 但是编译速度很慢 且需要很大内存
 */
public class JvmExpressionFunction extends ExpressionFunction {
    private static final Map<String, String> registeredMethods = new LinkedHashMap<>();
    private static final Pattern n = Pattern.compile("\\s*,\\s*");
    private final ManyToOneNumberFunction delegate;
    private final ParsedSignature parsed;

    private JvmExpressionFunction(ParsedSignature parsed, ManyToOneNumberFunction delegate) {
        super(null, parsed);
        this.delegate = delegate;
        this.parsed = parsed;
    }

    /**
     * 解析函数表达式并返回 JvmExpressionFunction 对象
     *
     * @param signature 函数签名 f(x) = xxx 的 表达式
     * @return JvmExpressionFunction 对象
     */
    public static JvmExpressionFunction parse(String signature) {
        final ParsedSignature parsed = parseFunctionSignature(signature);
        ManyToOneNumberFunction dyn = DynamicFunctionCompiler.compile(parsed.name, parsed.expression, parsed.paramNames);
        return new JvmExpressionFunction(parsed, dyn);
    }

    /**
     * 解析函数表达式并返回 JvmExpressionFunction 对象
     *
     * @param signature 函数签名 f(x) = xxx 的 表达式
     * @return JvmExpressionFunction 对象
     */
    public static JvmExpressionFunction parse(ExpressionFunction signature) {
        return parse(signature.toString());
    }

    /**
     * 转换为静态方法源码
     *
     * @param expression 函数表达式
     * @return 转换之后的静态方法源码
     */
    public static String convertToStaticMethodSource(String expression) {
        final ParsedSignature parsed = parseFunctionSignature(expression);
        final StringBuilder sb = new StringBuilder();
        sb.append("public static double ").append(parsed.name).append("(");
        for (int i = 0; i < parsed.paramNames.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("double ").append(parsed.paramNames[i]);
        }
        sb.append(") { return ").append(parsed.expression).append("; }");
        return sb.toString();
    }

    /**
     * 注册函数 到 JvmExpressionFunction
     *
     * @param methodExpr 函数表达式 f(x) = x + x 的数学格式即可！
     */
    public static void registerFunction(String methodExpr) {
        registerJvmFunction(convertToStaticMethodSource(methodExpr));
    }

    /**
     * 注册函数 到 JvmExpressionFunction
     *
     * @param methodSource 函数方法源码 需要是 Java 中的格式 如 public static double f(double x) { return x + x; }
     */
    public static void registerJvmFunction(String methodSource) {
        String trim = methodSource.trim();
        List<String> parts = new ArrayList<>();
        int parenIndex = trim.indexOf('(');
        if (parenIndex != -1) {
            String sig = trim.substring(0, parenIndex);
            parts.add(sig);
            parts.add(trim.substring(parenIndex));
        }
        if (parts.size() < 2) throw new IllegalArgumentException("函数方法格式错误：" + trim);
        String sig = parts.get(0);
        if (!sig.startsWith("public static ")) throw new IllegalArgumentException("函数方法签名格式错误：" + sig);
        String key = sig.substring("public static ".length());
        registeredMethods.put(key, methodSource);
    }

    public static Map<String, String> getRegisteredMethods() {
        return registeredMethods;
    }

    private static ParsedSignature parseFunctionSignature(String signature) {
        ArrayList<String> parts = StrUtils.splitByChar(signature, '=');
        if (parts.size() < 2) {
            throw new IllegalArgumentException("签名必须包含 '='; error: " + signature);
        }

        if (parts.size() > 2) {
            throw new IllegalArgumentException("签名不能包含多个 '='; error: " + signature);
        }

        String funcDef = parts.get(0).trim();
        String body = parts.get(1).trim();

        int openParen = funcDef.indexOf('(');
        int closeParen = funcDef.indexOf(')');

        if (openParen < 0 || closeParen < 0) {
            throw new IllegalArgumentException("函数定义需要包含参数列表");
        }

        final String funcName = funcDef.substring(0, openParen);
        if (StrUtils.containsNumber(funcName)) {
            throw new IllegalArgumentException("函数名称不能包含数字!!! => " + funcName);
        }
        final String paramsStr = funcDef.substring(openParen + 1, closeParen).trim();

        final String[] paramNames = paramsStr.isEmpty() ? new String[0] : n.split(paramsStr);

        return new ParsedSignature(funcName, paramNames, body);
    }

    @Override
    public double run(double... numbers) {
        if (numbers.length != this.parsed.paramNames.length) {
            throw new IllegalArgumentException("参数数量不匹配: 应为 " + this.parsed.paramNames.length + " 个, 实际为 " + numbers.length);
        }
        return delegate.run(numbers);
    }

    /**
     * 解释计算表达式，我们可以在这里传递一些参数，这些参数将做为函数的输入参数，在这里返回的就是函数带有数值的内部表达式
     * <p>
     * Explain the calculation expression. We can pass some parameters here, which will be used as input parameters for the function. Here, we return the internal expression of the function with numerical values
     *
     * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
     *                <p>
     *                This is the data input object for the function, and the framework passes the data input parameters here
     * @return 这里是数据经过函数转换之后的带有参数的表达式数据，用于构建数学表达式的！
     * <p>
     * This is the expression data with parameters after function conversion, used to construct mathematical expressions!
     */
    @Override
    public String explain(double... numbers) {
        throw new UnsupportedOperationException("JvmExpressionFunction 不支持解释表达式!!");
    }

    @Override
    public String toString() {
        return parsed.toString();
    }
}