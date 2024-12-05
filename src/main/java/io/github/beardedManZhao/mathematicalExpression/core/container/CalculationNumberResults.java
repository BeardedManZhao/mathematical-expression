package io.github.beardedManZhao.mathematicalExpression.core.container;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationNumberResults implements CalculationResults, Comparator<CalculationNumberResults>, Comparable<CalculationNumberResults> {

    private final static long serialVersionUID = "CalculationNumberResults".hashCode();
    private final double result;
    private final int layers;
    private String source;

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
    public CalculationNumberResults(int layers, double result, String source) {
        this.layers = layers;
        this.result = result;
        this.source = source;
    }

    @Override
    public int getResultLayers() {
        return this.layers;
    }

    @Override
    public String getCalculationSourceName() {
        return this.source;
    }

    public Double getResult() {
        return this.result;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public BigDecimal getBigDecimalResult() {
        return BigDecimal.valueOf(this.result);
    }

    @Override
    public int compare(CalculationNumberResults o1, CalculationNumberResults o2) {
        return o1.compareTo(o2);
    }

    @Override
    public int compareTo(CalculationNumberResults o) {
        if (o == null) return 1;
        else {
            return (int) Math.ceil(this.result - o.result);
        }
    }

    @Override
    public String toString() {
        return "CalculationNumberResults{" +
                "result=" + result +
                ", source='" + source + '\'' +
                '}';
    }
}
