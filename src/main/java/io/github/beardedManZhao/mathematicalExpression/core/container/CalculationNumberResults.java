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

    /**
     * 在  getDoubles 被正式删除之前 此变量会做为 getDoubles() 的返回值
     */
    private final static Double[] tempDoubles = new Double[0];

    private final static long serialVersionUID = "CalculationNumberResults".hashCode();
    private final Double[] doubles;
    private final double result;
    private final String source;
    private final int layers;

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
        this.doubles = tempDoubles;
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
    public BigDecimal getBigDecimalResult() {
        return BigDecimal.valueOf(this.result);
    }

    /**
     * @return 计算时候的聚合过程数值组，会将每一次计算的结果存储在这里，这里的数据具体意义，应在于计算组件对于该数据的存储操作，本类只存储，不计算
     * <p>
     * The aggregation process numerical group during calculation will store the results of each calculation here. The specific meaning of the data here should be the storage operation of the calculation component on this data. This class only stores and does not calculate
     * @deprecated 此函数的存在不必要，您如果希望探索到计算的过程 完全可以使用 `explain` 替代，因为这里的记录不全，且开销较大，预计在 1.4.x 版本中弃用。
     * <p>
     * The existence of this function is unnecessary. If you want to explore the calculation process, you can use 'explain' instead because the records here are incomplete and the cost is high. It is expected to be abandoned in version 1.4. x
     */
    @Deprecated
    public Double[] getDoubles() {
        return doubles;
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
