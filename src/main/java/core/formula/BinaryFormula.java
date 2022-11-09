package core.formula;

import exceptional.AbnormalOperation;
import utils.NumberUtils;

/**
 * 双参数的数学表达式
 */
public abstract class BinaryFormula<result> implements Formula<result, BinaryFormula<result>> {

    private final result a;
    private final result b;
    private final result resultNumber;
    private final int operatorNumber;

    public BinaryFormula(result a, result b, result resultNumber, int operatorNumber) {
        this.a = a;
        this.b = b;
        this.resultNumber = resultNumber;
        if (operatorNumber == NumberUtils.ERROR) {
            throw new AbnormalOperation("数学计算表达式解析异常，找到了错误的表达式运算符号。\n" +
                    "The mathematical calculation expression parsing is abnormal, and the wrong expression operation symbol is found." +
                    "Bad expression operator => " + operatorNumber);
        }
        this.operatorNumber = operatorNumber;
    }

    protected BinaryFormula(result resultNumber) {
        this.resultNumber = resultNumber;
        a = null;
        b = null;
        operatorNumber = NumberUtils.ERROR;
    }

    /**
     * @return 拓展本类至其子类实现类，能够通过该函数，实现父类与子类之间的切换
     * <p>
     * Expand this class to its subclass implementation class, and switch between parent class and subclass through this function
     */
    @Override
    public BinaryFormula<result> extendClass() {
        return this;
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
    public int compareTo(BinaryFormula<result> o) {
        return this.operatorNumber - o.operatorNumber;
    }

    public result getA() {
        return a;
    }

    public result getB() {
        return b;
    }

    public result getResultNumber() {
        return resultNumber;
    }

    public int getOperatorNumber() {
        return operatorNumber;
    }

    @Override
    public String toString() {
        return a + " [" + operatorNumber + "] " + b + " = " + resultNumber;
    }
}
