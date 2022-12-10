package core.container;

import java.util.Comparator;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationBooleanResults implements CalculationResults, Comparator<CalculationBooleanResults>, Comparable<CalculationBooleanResults> {
    private final boolean result;
    private final String source;
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

    public boolean getResult() {
        return this.result;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
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

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
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
