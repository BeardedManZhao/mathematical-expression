package io.github.beardedManZhao.mathematicalExpression.core.calculation.number;

import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;

import java.math.BigDecimal;

/**
 * 适用于 BigDecimal 操作的计算结果
 *
 * @author zhao
 */
public class CalculationBigDecimalResults extends CalculationNumberResults {
    private final BigDecimal result;

    /**
     * @param layers 结果的计算层数
     *               <p>
     *               Calculation level of results
     * @param result 已经计算出结果数值的情况下，使用该形参进行赋值
     *               <p>
     *               If the result value has been calculated, use this parameter for assignment
     * @param source 来源，表明该结果对象的计算来源。
     *               <p>
     *               Source indicates the calculation source of the result object.
     */
    public CalculationBigDecimalResults(int layers, BigDecimal result, String source) {
        super(layers, result.doubleValue(), source);
        this.result = result;
    }

    @Override
    public BigDecimal getBigDecimalResult() {
        return this.result;
    }
}
