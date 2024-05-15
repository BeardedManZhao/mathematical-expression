package io.github.beardedManZhao.mathematicalExpression.utils;

import io.github.beardedManZhao.mathematicalExpression.exceptional.AbnormalOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 特殊 BigDecimal 计算类
 *
 * @author zhao
 */
public final class CalculationOptimized {
    private static final Map<Character, BiFunction<BigDecimal, BigDecimal, BigDecimal>> OPERATION_MAP;

    static {
        OPERATION_MAP = new HashMap<>();
        OPERATION_MAP.put('+', BigDecimal::add);
        OPERATION_MAP.put('-', BigDecimal::subtract);
        OPERATION_MAP.put('*', BigDecimal::multiply);
        OPERATION_MAP.put('/', BigDecimal::divide);
        OPERATION_MAP.put('%', BigDecimal::remainder);
        OPERATION_MAP.put('^', (a, b) -> a.pow((int) b.doubleValue()));
    }

    public static BigDecimal calculation(char CalculationType, BigDecimal an, BigDecimal bn) {
        BiFunction<BigDecimal, BigDecimal, BigDecimal> operation = OPERATION_MAP.get(CalculationType);
        if (operation == null) {
            throw new AbnormalOperation("操作数计算异常，您的计算模式不存在，错误的计算模式 = [" + CalculationType + "]");
        }
        return operation.apply(an, bn);
    }
}