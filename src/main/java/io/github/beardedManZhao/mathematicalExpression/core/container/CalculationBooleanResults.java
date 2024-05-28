package io.github.beardedManZhao.mathematicalExpression.core.container;

import java.util.Comparator;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationBooleanResults implements CalculationResults, Comparator<CalculationBooleanResults>, Comparable<CalculationBooleanResults> {

    private static final long serialVersionUID = "CalculationBooleanResults".hashCode();

    private final boolean result;
    private String source;
    private final int Layers;
    private final double left;
    private final double right;

    /**
     * @param result 已经计算出结果数值的情况下，使用该形参进行赋值
     * @param source 来源，表明该结果对象的计算来源。
     * @param layers 计算层次
     * @param left   计算出来的表达式左值，在进行比较的时候，往往是两个表达式之间进行比较，在这里就可以获取比较运算符左边的数值
     *               <p>
     *               The calculated left value of the expression is often compared between two expressions when comparing. Here you can get the value on the left of the comparison operator
     * @param right  计算出来的表达式右值，在进行比较的时候，往往是两个表达式之间进行比较，在这里就可以获取比较运算符右边的数值
     *               <p>
     *               The right value of the calculated expression is often compared between two expressions during comparison. Here you can get the value on the right of the comparison operator
     */
    public CalculationBooleanResults(boolean result, String source, int layers, double left, double right) {
        this.result = result;
        this.source = source;
        Layers = layers;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getResultLayers() {
        return this.Layers;
    }

    @Override
    public String getCalculationSourceName() {
        return this.source;
    }

    public Boolean getResult() {
        return this.result;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    @Override
    public int compareTo(CalculationBooleanResults o) {
        if (o == null) return 1;
        else {
            if (this.result ^ o.result) {
                if (this.result) return 1;
                else return -1;
            } else return 0;
        }
    }

    @Override
    public int compare(CalculationBooleanResults o1, CalculationBooleanResults o2) {
        return o1.compareTo(o2);
    }

    @Override
    public String toString() {
        return "CalculationBooleanResults{" +
                "result=" + result +
                ", source='" + source + '\'' +
                ", Layers=" + Layers +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
