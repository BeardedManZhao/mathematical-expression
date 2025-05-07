package io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm;

/**
 * 表示解析后的函数签名信息
 *
 * @author 赵凌宇
 */
public class ParsedSignature {
    final String name;
    final String[] paramNames;
    final String expression;

    ParsedSignature(String name, String[] paramNames, String expression) {
        this.name = name;
        this.paramNames = paramNames;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return name + "(" + String.join(",", paramNames) + ")=" + expression;
    }
}
